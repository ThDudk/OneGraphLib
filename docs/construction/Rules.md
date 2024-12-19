hello

| Restrictions     | Required          | Required |
|------------------|-------------------|----------|
| Loops            |
| Multiple Edges   | Complicated Edges |
| Cycles           | Multiple Edges    | Loops    |
| Degrees          |
| Disconnectedness |

```java
Graph.Builder builder = Graph.newBuilder() 
    .withNode(new Node("A"))
    .withNode(new Node("B"))
    .withEdge("A", "B", )

```

```java
Node node1 = new data(1, 2, 3)
Node node2 = new data(2, 2, 3)
Graph graph = Graph.newFactory()
    .setLoops(false)
    .weighted() <- weightedGraphBuilder
    .addNodes(node1, node2)
    .addWeightedEdge (node1, new path(4), node2)
    .addNeighbor(node1, node2) <-- error
    .chain(1, 2, 3, 4, 5)
    .build();

graph.shortestWeightedPath(node1, node2);
```
