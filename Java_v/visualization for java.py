import pandas as pd
import matplotlib.pyplot as plt

data_120 = pd.read_csv(r"ising_L120.csv", encoding = "ISO-8859-1")

m = data_120['Magnetización'].to_list()


plt.plot(m)
plt.ylabel('Magnetización')
plt.show()

'''plt.subplot(2,2,3)
plt.plot(e)
plt.ylabel('Energía')
plt.xlabel('Paso')
plt.subplot(1,2,2)
plt.imshow(S) #plotea el estado final, dandole un color al 1 y otro al -1
plt.title(f'Red de Ising de lado {L}')
plt.show()'''