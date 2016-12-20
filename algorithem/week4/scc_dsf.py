

explored = []
finished = []
results = []


def make_graph(edges):
    g = {}
    for v, w in edges:
        node = (w, false)
        if not v in g:
            g[v] = []
        g[v].append(node)
    return g
        
def reverse_graph(edges):
    g = {}
    for v, w in edges:
        if not w in g:
            g[w] = []
        g[w].append(v)
    return g
 

def dsf_loop1(g):
    global explored
    explored = []

    for v in g:
        if not v in explored:
            explored.append(v)
            dsf_1(g, v)

def dsf_loop2(g):
    global explored
    global finished
    global results
    explored = []

    while finished:
        v = finished.pop()
        result = []
        if not v in explored:
            explored.append(v)
            if v in g:
                dsf_2(g, v, result)
            else:
                # in this case, v is only vetex
                result = [v]

        if result:
            results.append(result)
        

def dsf_1(g, v):
    global explored
    global finished

    for w in g[v]:
        if not w in explored:
            explored.append(w)
            if w in g:
                dsf_1(g, w)
    finished.append(v)


def dsf_2(g, v, result):
    global explored
    for w in g[v]:
        if not w in explored:
            explored.append(w)
            if w in g:
                dsf_2(g, w, result)
    result.append(v)


import sys
sys.setrecursionlimit(1048576)
edges = [line.split() for line in open("scc.txt", "r")]
g = make_graph(edges)
print("--- graph made ---")
dsf_loop1(g)
print("--- first dsf done ---")

g = reverse_graph(edges)
print("--- reverse graph made ---")
dsf_loop2(g)
print("--- second dsf done ---")

g = {}
finished = []
explored = []
scc_len = []

for result in results:
    scc_len.append(len(result))

print("--- got scc result ---")

while len(scc_len) < 5:
    scc_len.append(0)
scc_len.sort(reverse=True)
print("--- got scc length list ---")

print("The largest scc: ", scc_len[:10])

