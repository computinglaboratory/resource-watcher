package com.computinglaboratory.resourcewatcher.linux

import com.computinglaboratory.resourcewatcher.ResourceWatcherFactory
import spock.lang.*

class RamWatcherLinuxTest extends Specification {

    def "gives non null total memory report"() {
        given:
        def systemRamWatcher = ResourceWatcherFactory.create().ramWatcher

        when:
        def totalMemory = systemRamWatcher.getTotalMemory()

        then:
        totalMemory != null && totalMemory != 0
    }

    def "gives non null free memory report"() {
        given:
        def systemRamWatcher = ResourceWatcherFactory.create().ramWatcher

        when:
        def freeMemory = systemRamWatcher.getFreeMemory()

        then:
        freeMemory != null && freeMemory != 0
    }

    def "gives non null available memory report"() {
        given:
        def systemRamWatcher = ResourceWatcherFactory.create().ramWatcher

        when:
        def availableMemory = systemRamWatcher.getAvailableMemory()

        then:
        availableMemory != null && availableMemory != 0
    }

    def "gives non null cached report"() {
        given:
        def systemRamWatcher = ResourceWatcherFactory.create().ramWatcher

        when:
        def buffers = systemRamWatcher.getCached()

        then:
        buffers != null && buffers != 0
    }

    def "gives non null buffers report"() {
        given:
        def systemRamWatcher = ResourceWatcherFactory.create().ramWatcher

        when:
        def buffers = systemRamWatcher.getBuffers()

        then:
        buffers != null && buffers != 0
    }
}
