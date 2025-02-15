package com.example.circuitbr.service

import com.example.circuitbr.adapter.AlbumsAdapter
import com.example.circuitbr.dto.Album
import com.example.circuitbr.dto.AlbumsResponse
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.web.client.ResourceAccessException
import spock.lang.Specification
import spock.lang.Unroll
import spock.util.concurrent.PollingConditions

@SpringBootTest
class AlbumServiceSpec extends Specification {

    @Autowired
    AlbumService albumService

    @SpringBean
    AlbumsAdapter albumsAdapter = Mock()

    def conditions = new PollingConditions(timeout: 20, delay: 0.5)

    def "should return albums when service is healthy"() {
        given: "the albums adapter returns a successful response"
        def response = new AlbumsResponse()
        response.setAlbums([])  // Initialize with empty list
        albumsAdapter.getAlbums() >> response

        when: "we request albums"
        def result = albumService.getAlbums()

        then: "we get the expected response"
        result == response
    }

    def "should trigger circuit breaker and return fallback after multiple failures"() {
        given: "the albums adapter fails consistently"
        albumsAdapter.getAlbums() >> { throw new ResourceAccessException("Service unavailable") }

        when: "we make multiple requests to trigger the circuit breaker"
        def results = []
        15.times {
            try {
                results << albumService.getAlbums()
            } catch (Exception e) {
                results << e
            }
        }

        then: "eventually we should get fallback responses"
        conditions.eventually {
            def fallbackResponses = results.findAll { it instanceof AlbumsResponse }
            assert fallbackResponses.size() > 0
            assert fallbackResponses.every { it.albums != null && it.albums.isEmpty() }
        }
    }

    def "should recover when service becomes healthy again"() {
        given: "a mock response for when service recovers"
        def response = new AlbumsResponse()
        response.setAlbums([new Album(id: 1, title: "Dark side of the moon")])  // Initialize with empty list
        def callCount = 0

        and: "the adapter fails first then recovers"
        albumsAdapter.getAlbums() >> {
            callCount++
            if (callCount <= 10) {
                throw new ResourceAccessException("Service unavailable")
            }
            return response
        }

        when: "we make requests through the failure and recovery period"
        def results = []
        conditions.eventually {
            // Make requests until we get a successful response
            def result = albumService.getAlbums()
            results << result
            assert result == response
        }

        then: "we should see the progression from failures to success"
        def fallbacks = results.findAll { it.albums != null && it.albums.isEmpty() }
        def successes = results.findAll { it == response }
        fallbacks.size() > 0
        successes.size() > 0
    }
} 