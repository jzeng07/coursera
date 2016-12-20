
def read_file(f):
    lines = [x for x in open(f, "r")]
    data = []
    vertex_size = int(lines[0])

    for line in lines[1:]:
        v, w, dist = line.split()
        v = int(v)
        w = int(w)
        dist = int(dist)
        data.append((dist, v, w))
    return vertex_size, sorted(data)


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


def summary(uf):
    clusters = {}
    for d in uf.tree:
        rd = uf.find(d)
        if rd in clusters:
            clusters[rd].append(d)
        else:
            clusters[rd] = [d]
    return clusters


def cluster(f):
    vertex_size, data = read_file(f)
    vertex = [x+1 for x in range(vertex_size)]

    uf = UnionFind(vertex)
    k = vertex_size
    max_dist = 0

    for i, e in enumerate(data):
        cost, v, w = (e[0], e[1], e[2])
        d_size = uf.union(v, w)
        k -= d_size

        if k <= 4:
            break
  
    clusters = summary(uf)
    spacings = {}
    max_dist = 10000
    for e in data:
        cost, v, w = (e[0], e[1], e[2])
        rv = uf.find(v)
        rw = uf.find(w)
        if rv == rw:
            continue
        if cost < max_dist:
            max_dist = cost

    return max_dist
        
            

print cluster("cluster1.txt")
