package io.github.kamilszewc.resourcewatcher.watchers.linux;

import io.github.kamilszewc.resourcewatcher.core.Bandwidth;
import io.github.kamilszewc.resourcewatcher.core.ProcessCommand;
import io.github.kamilszewc.resourcewatcher.exceptions.NoNetworkInterfaceException;
import io.github.kamilszewc.resourcewatcher.watchers.interfaces.NetworkWatcher;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public class NetworkWatcherLinux implements NetworkWatcher {
    private Long getInterfaceProcNetDevInfo(final String interfaceName, final int idx) throws NoNetworkInterfaceException, IOException {

        String result = ProcessCommand.call("cat /proc/net/dev");
        List<String> lines = Arrays.stream(result.split("\n")).collect(Collectors.toList());
        Optional<String> line = lines.stream()
                .filter(l -> l.split(":")[0].strip().equals(interfaceName))
                .findAny();

        if (line.isPresent()) {
            return Long.valueOf(line.get().strip().replaceAll("\\s+", " ").split(" ")[idx]);

        } else {
            throw new NoNetworkInterfaceException(interfaceName);
        }
    }

    @Override
    public List<String> getListOfInterfaces() throws IOException {

        String result = ProcessCommand.call("cat /proc/net/dev");
        List<String> lines = Arrays.stream(result.split("\n")).collect(Collectors.toList());
        List<String> interfaces = lines.stream()
                .filter(line -> line.contains(":"))
                .map(line -> line.split(":")[0].strip())
                .collect(Collectors.toList());

        return interfaces;
    }

    @Override
    public Bandwidth getInterfaceReceiveSpeed(String interfaceName) throws NoNetworkInterfaceException, IOException {
        Long bytesFirst = getInterfaceProcNetDevInfo(interfaceName, 1);
        long timeFirst = System.currentTimeMillis();
        Long bytesSecond = getInterfaceProcNetDevInfo(interfaceName, 1);
        long timeSecond = System.currentTimeMillis();

        Double speed = Double.valueOf(bytesSecond - bytesFirst) * 1000 / (timeSecond - timeFirst);

        return new Bandwidth(speed);
    }

    @Override
    public Bandwidth getInterfaceTransmitSpeed(String interfaceName) throws NoNetworkInterfaceException, IOException {
        Long bytesFirst = getInterfaceProcNetDevInfo(interfaceName, 9);
        long timeFirst = System.currentTimeMillis();
        Long bytesSecond = getInterfaceProcNetDevInfo(interfaceName, 9);
        long timeSecond = System.currentTimeMillis();

        Double speed = Double.valueOf(bytesSecond - bytesFirst) * 1000 / (timeSecond - timeFirst);

        return new Bandwidth(speed);
    }

}
