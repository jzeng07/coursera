
import heapq
import sys
from pprint import pprint

def dijkstra(g, source):
    
    path = {source: [0, []]}
    vertices = set(g.keys())
    visited = set([source])

    # initialize
    for other in vertices - visited:
        path[other] = [-1, []]

    current = source
    parent = None
    heap = []
    while vertices - visited:
        for vertex in g[current]:
            if vertex[0] not in visited:
                distance = path[current][0] + vertex[1]

                if path[vertex[0]][0] == -1 or distance < path[vertex[0]][0]:
                    path[vertex[0]][0] = distance

                heapq.heappush(heap, (distance, vertex[0], current))

        if parent:
            if not path[current][1]:
                path[current][1] = [x for x in path[parent][1]]
                path[current][1].append(current)
        
        visited.add(current)
        if heap:
            _next = heapq.heappop(heap)
            current = _next[1]
            parent = _next[2]

    return path


def main(argv):
    case_file = argv[0]
    g = {}
    with open(case_file, "r") as f:
        for line in f.readlines():
            line = line.replace("\t", " ")
            vertex, edges = line.strip().split(" ", 1)
            g[vertex] = []
            for edge in edges.split():
                _vertex, _weight = edge.split(",")
                g[vertex].append((_vertex, int(_weight)))
    shortest_path = dijkstra(g, "1")

    answer = []
    for i in (7,37,59,82,99,115,133,165,188,197):
        answer.append(shortest_path[str(i)][0])
    print ",".join(map(str,answer))


if __name__ == "__main__":
   sys.exit(main(sys.argv[1:]))

