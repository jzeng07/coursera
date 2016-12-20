
import random

def mincut(adj_list):

    while len(adj_list.keys()) > 2:
        rand_v = random.choice(adj_list.keys())
        v_of_e = adj_list[rand_v]
        rand_w = random.choice(v_of_e)
        for v in v_of_e:
            for i, _v in enumerate(adj_list[v]):
                if _v == rand_v:
                    adj_list[v][i] = rand_w
                    if v == rand_w:
                        del(adj_list[v][i])
            if v != rand_v and v != rand_w:
                adj_list[rand_w].append(v)
        del(adj_list[rand_v])
    
        
mincuts = []

for i in range(1000):
    adj_list = {}
    for line in open("kargeMinCut.txt", "r"):
        vertex = line.split()
        adj_list[vertex[0]] = vertex[1:]


    mincut(adj_list)
    for k in adj_list:
        mincuts.append(len(adj_list[k]))
        break

mincuts.sort()
print "1000 mincuts: ", mincuts
