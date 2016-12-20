

from collections import deque


def bsf(g, v):
    explored = []
    queue = deque([])
    queue.append(v)
    explored.append(v)
    while queue:
        vetex = queue.popleft()
        print "explored: ", explored
        print "%s ---- %s" % (vetex, g[vetex])
        for w in g[vetex]:
            if w not in explored:
                queue.append(w)
                explored.append(w)
    return explored


G = {}
for line in open("case1_1.txt", "r"):
    vertex = line.split()
    G[vertex[0]] = vertex[1:]

explored = bsf(G, "2")
print "explored: ", explored
