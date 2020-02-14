package domain;

import java.util.concurrent.Semaphore;

public class ContadorMesas {
	int maxMesas;
	int[] contadorMesa;
	int[] asignaMesaPuesto;
	Semaphore lock = new Semaphore(1);
	
	public ContadorMesas(int maxMesas) {
		try {
			lock.acquire();
			this.maxMesas = maxMesas;
			this.contadorMesa = new int[maxMesas];
			this.asignaMesaPuesto = new int[maxMesas];
			//iniciamos todos los contadores a cero
			for (int i = 0 ; i < maxMesas ; i++) {
				this.contadorMesa[i] = 0;
				this.asignaMesaPuesto[i] = 0;
			}
			lock.release();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public boolean asignaMesa(int mesaAsignada, int puesto) {
		boolean result = false;
		try {
			lock.acquire();
			if (asignaMesaPuesto[mesaAsignada] == 0) {
				asignaMesaPuesto[mesaAsignada] = puesto;
				result = true;
			}
			lock.release();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public void nextContador(int mesaAsignada) {
		try {
			lock.acquire();
			contadorMesa[mesaAsignada]++;
			lock.release();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public int getContador(int mesaAsignada) {
		int contador = -1;
		try {
			lock.acquire();
			contador = contadorMesa[mesaAsignada];
			lock.release();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return contador;
	}
}
