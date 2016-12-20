
def make_graph(edges):
    g = {}
    for v, w in edges:
        if not v in g:
            g[v] = set([])
        g[v].add(w)
    return g
        
def reverse_graph(edges):
    g = {}
    for v, w in edges:
        if not w in g:
            g[w] = set([])
        g[w].add(v)
    return g
 

def dsf_loop(edges):
    leaders = []
    g = make_graph(edges)
    print("--- graph made ---")
    visited = set([])
    scc_list = []
    import pdb; pdb.set_trace()
    for v in g:
        if v in visited:
            continue
        visited = dsf(g, v, leaders, visited)
    print("--- first dfs done ---")

    g = reverse_graph(edges)
    print("--- reverse graph made ---")
    scc_list = []
    visited = set([])
    _visited = set([])
    while leaders:
        leader = leaders.pop()
        if leader in g:
            visited = dsf(g, leader, [], visited)
            scc = visited - _visited
            _visited = visited.copy()
            if visited:
                scc_list.append(scc)
        else:
            scc_list.append(set([leader]))
    print("--- second dfs done ---")

    scc_len = []
    for scc in scc_list:
        scc_len.append(len(scc))

    while len(scc_len) < 5:
        scc_len.append(0)
    scc_len.sort(reverse=True)
    print("scc len: %s" % scc_len[:10])


def dsf(g, start, leaders, visited=None):
    if start not in g:
        return []
    stack = [start]
    visited = set([])
    while stack:
        vetex = stack.pop()
        if vetex not in g:
            continue
        if vetex not in visited:
            stack.append(vetex)
            visited.add(vetex)
            stack.extend(g[vetex] - visited)
        else:
            if not vetex in leaders:
                leaders.append(vetex)
    return visited


def dsf_2(g, start, visited=None):
    if not visited:
        visited = set([])
    visited.add(start)
    for next in g[start] - visited:
        return dsf_2(g, next, visited)
    return visited

import sys
sys.setrecursionlimit(100000)
edges = [line.split() for line in open("scc.txt", "r")]
dsf_loop(edges)
