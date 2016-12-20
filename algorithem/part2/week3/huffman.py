
import heapq
import sys

def read_file(f):
    pq = []
    heapq.heapify(pq)
    lines = [x for x in open(f, "r")]
    i = 1
    for line in lines[1:]:
        heapq.heappush(pq, (int(line), str(i)))
        i += 1
    return pq
        


def append_tree(data, trie):
    for d1 in data[0].split("-"):
        trie[d1] = "0" + trie.get(d1, "")
    for d2 in data[1].split("-"):
        trie[d2] = "1" + trie.get(d2, "")
    return trie


def huffman(t, trie):
    if len(t) == 2:
        symbol1 = t[0][1]
        symbol2 = t[1][1]
        return append_tree([symbol1, symbol2], trie)

    d1 = heapq.heappop(t)
    d2 = heapq.heappop(t)
    p1 = d1[0]
    p2 = d2[0]
    symbol1 = d1[1]
    symbol2 = d2[1]
    heapq.heappush(t, ((p1 + p2), "%s-%s" % (symbol1, symbol2)))
    trie = append_tree([symbol1, symbol2], trie)
    return huffman(t, trie)


def main(f):
    sys.setrecursionlimit(1500)
    data = read_file(f)
    trie = {}
    trie = huffman(data, trie)
    t = []
    for symbol, depth in trie.iteritems():
        t.append((len(depth), symbol))
    t = sorted(t)
    print "min depth of %s: %s" % (t[0][1], t[0][0])
    print "max depth of %s: %s" % (t[-1][1], t[-1][0])


if __name__ == "__main__":
    main(sys.argv[1])
