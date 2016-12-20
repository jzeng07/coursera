"""
 find the k-th samll element in a list
"""

import random

def swap(l, i, j):
    tmp = l[i]
    l[i] = l[j]
    l[j] = tmp

def selection(l, k):
    if len(l) == 1:
        return l[0]

    pivot = l[0]
    i = 1
    for j in range(1, len(l)):
        if l[j] < pivot:
            swap(l, i, j)
            i += 1
    swap(l, 0, i-1)
    if i - 1 == k:
        return l[i-1]
    elif i - 1 > k:
        return selection(l[:i-1], k)
    else:
        return selection(l[i:], k - i)
   


l = [3, 4, 2, 8, 5, 1, 9]
l = [random.randint(1, 1000) for x in xrange(100)]
l = list(set(l))
l1 = [x for x in l]

k = 90
l1.sort()
a1 = l1[k]
print "the %s small element is: %s" % (k, a1)

a = selection(l, k)
print "the %s small element is: %s" % (k, a)

print a == a1
