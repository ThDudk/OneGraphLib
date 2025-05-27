package io.github.thdudk.graphs;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.*;

@ToString
@EqualsAndHashCode
public class TwoWayDataContainer<ID, DATA> {
    @RequiredArgsConstructor
    @Getter
    public static class IdDataPair<ID, DATA> {
        private final ID id;
        private final DATA data;
    }

    private final Map<ID, DATA> nodeData;
    private final Map<DATA, List<ID>> dataToID;

    public TwoWayDataContainer(Map<ID, DATA> nodeData) {
        this.nodeData = nodeData;

        dataToID = new HashMap<>();
        for(Map.Entry<ID, DATA> entry : nodeData.entrySet()) {
            dataToID.putIfAbsent(entry.getValue(), new ArrayList<>());
            dataToID.get(entry.getValue()).add(entry.getKey());
        }
    }

    public DATA getData(ID id) {
        return nodeData.get(id);
    }
    public Collection<ID> getIDs(DATA data) {
        if(!dataToID.containsKey(data)) throw new RuntimeException("Data not found: " + data);

        return dataToID.get(data);
    }
}
