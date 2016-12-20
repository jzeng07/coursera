
import sys

def read_file(f):
    data = [0]
    lines = [x for x in open(f, "r")]
    for line in lines[1:]:
        data.append(int(line))
    return data


def mwis(data):
    A = [0] * len(data)
    A[1] = data[1]
    for i in range(2, len(data)):
        A[i] = max(A[i-1], (A[i-2] + data[i]))

    S = []
    i = len(A) - 1
    while i >= 1:
        if A[i-1] > A[i-2] + data[i]:
            i -= 1
        else:
            S.append(i)
            i -= 2
    return S


def verify(wiset):
    v_set = (1, 2, 3, 4, 17, 117, 517, 997)
    s = [0] * 8
    i = 0
    for v in v_set:
        if v in wiset:
            s[i] = 1
        i += 1
    return s
        

def main(f):
    data = read_file(f)
    wiset = mwis(data)
    s = verify(wiset)
    print s


if __name__ == "__main__":
    main(sys.argv[1])
