import pandas

csv = pandas.read_csv('./res.csv', sep=';')
grouped = csv.groupby(['iterations', 'threads'])

print('mean:')
print(grouped.mean())

print('stddev:')
print(grouped.std())

