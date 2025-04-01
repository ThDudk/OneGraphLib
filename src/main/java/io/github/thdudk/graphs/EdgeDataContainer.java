package io.github.thdudk.graphs;

import io.github.thdudk.ids.EdgeID;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.*;

@EqualsAndHashCode
@ToString
public class EdgeDataContainer<E> {
    private final Map<EdgeID, E> idToData;
    private final Map<E, List<EdgeID>> dataToID;

    public EdgeDataContainer(Map<EdgeID, E> edgeData) {
        this.idToData = edgeData;

        dataToID = new HashMap<>();
        for(Map.Entry<EdgeID, E> entry : idToData.entrySet()) {
            dataToID.putIfAbsent(entry.getValue(), new ArrayList<>());
            dataToID.get(entry.getValue()).add(entry.getKey());
        }
    }

    public E getData(EdgeID id) {
        return idToData.get(id);
    }
    public Collection<EdgeID> getIDs(E data) {
        if(!dataToID.containsKey(data)) throw new RuntimeException("Data not found: " + data);

        return dataToID.get(data);
    }
}
