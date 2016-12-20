import heapq

def read_data(f):
    G = {}
    lines = [l for l in open(f, "r")]
    for line in lines[1:]:
        v, w, cost = line.strip().split()
        if int(v) in G:
            G[int(v)].add((int(cost), int(w)))
        else:
            G[int(v)] = set([(int(cost), int(w))])

        if int(w) in G:
            G[int(w)].add((int(cost), int(v)))
        else:
            G[int(w)] = set([(int(cost), int(v))])
    return G


def mst(f):
    G = read_data(f)
    mst_v = set([])
    mst = set([])
    mst_sum = 0
    heap = []
    heapq.heapify(heap)

    v = sorted(G.keys())[0]
    mst_v.add(v)
    for e in G[v]:
        heapq.heappush(heap, (e, v))

    while heap:
        (_cost, _v), v = heapq.heappop(heap)
        if _v in G and not _v in mst_v:
            for _e in G[_v]:
                heapq.heappush(heap, (_e, _v))

        if not _v in mst_v:
            mst_v.add(_v)
            mst.add((v, _v))
            mst_sum += _cost

    return mst_sum

print mst("edges.txt")
