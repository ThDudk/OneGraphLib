## VoidData
While realistically there is little reason to contain repeat data and this can be circumvented with a UUID value, you may sometimes want to lose node or edge data to reduce the graph's memory or storage size. As such it makes sense to implement some sort of class that contains only a UUID or unique value to allow for no node data.

This would allow graphs to contain no node data yet still store nodes in any Set based implementation.
For edges, it's as easy as using `Graph<VoidNode>`, as they already contain no edge data. 

### implementation
In terms of implementation, there are a few ways `VoidNode` could be implemented.

First would be by simple having it wrap a UUID value. There is no need to consider further as UUID solutions take up little storage, support massive Graph sizes and have ~0% chance of repeats.

It may make sense to allow the GraphSerializer to optimize Json formatting by ignoring VoidNode data, and serializing as VoidNode if node data isn't provided. 