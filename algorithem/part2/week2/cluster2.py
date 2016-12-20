
import time

def read_file(f):
    lines = [x for x in open(f, "r")]
    vertex_num, vertex_size = lines[0].split()
    vertex_num = int(vertex_num)
    vertex_size = int(vertex_size)
    data = set([])

    for line in lines[1:]:
        v = "".join(line.split())
        v = int(v, 2)
        data.add(v)
    return vertex_num, vertex_size, data


def neibors(data):
    if not isinstance(data, list):
        data = [data]
    neibs = []
    k = 0b111111111111111111111111
    m = 0b111111111111111111111110
    n = 0b000000000000000000000001
    for d in data:
        for i in range(24):
            if i:
                _m = ~(-m << i - 1) & k
                _n = n << i
            else:
                _m = m
                _n = n
            _d = d & _m
            if _d == d:
                _d = d | _n
            neibs.append(_d)
    return neibs


class UnionFind(object):
    def __init__(self, vertex):
        self.tree = {v: [v, 1] for v in vertex}

    def connected(self, v, w):
        rv = self.find(v)
        rw = self.find(w)
        return rv == rw

    def find(self, v):
        while v != self.tree[v][0]:
            v = self.tree[v][0]
        return v

    def union(self, v, w):
        rv = self.find(v)
        rw = self.find(w)
        if rv == rw:
            return 0

        rvn = self.tree[rv]
        rwn = self.tree[rw]
        if rvn[1] > rwn[1]:
            rwn[0] = rv
            rvn[1] += rwn[1]
        else:
            rvn[0] = rw
            rwn[1] += rvn[1]
        return 1


def summary(uf, data):
    clusters = {}
    for d in data:
        rd = uf.find(d)
        if d in clusters:
            clusters[rd].append(d)
        else:
            clusters[rd] = [d]
    return clusters
    

def cluster(f):
    vertex_num, vertex_size, data = read_file(f)
    uf = UnionFind(data)
    clusters_num = len(uf.tree.keys())

    for d in data:
        neibors_1 = neibors(d)
        neibors_2 = neibors(neibors_1)
        neibors_1.extend(neibors_2)
        for neib in neibors_1:
            if neib in data:
                d_size = uf.union(d, neib)
                clusters_num -= d_size
                print "clusters_num=%s" % clusters_num

    clusters = summary(uf, data)
    return clusters_num



now = time.time()
print cluster("clustering_big.txt")
print time.time() - now
