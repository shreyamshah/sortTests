import java.awt.Dimension;
import java.io.*;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class sortTest {
	public static int selection;

	public static void main(String args[]) throws Exception {
		selection = Integer.parseInt(args[0]);
		ArrayList<Integer> integers = new ArrayList<Integer>();
		createAndWriteInitFile();
		long start = System.nanoTime();
		for (int j = 0; j < 8; j++) {
			File f = new File("B:/Projects/AAC_Shreyam/AAC_Shreyam/mergeSort/src/" + j + ".txt");
			integers.addAll(readIntermediateFile(j));
			// sc.close();
			if (selection == 0)
				insertion(integers);
			else if (selection == 1)
				mergesort(integers);
			else if (selection == 2)
				quickSort(integers);
			FileOutputStream fos = new FileOutputStream(f);
			fos.write('\0');
			File nf = new File("B:/Projects/AAC_Shreyam/AAC_Shreyam/mergeSort/src/" + j + ".txt");
			nf.createNewFile();
			FileWriter fw = new FileWriter(nf);
			for (int i = 0; i < integers.size(); i++) {
				// System.out.println(integers.get(i)+"\n");
				fw.write(integers.get(i) + "\n");
			}
			integers.clear();
			fos.close();
			fw.close();
		}
		int totalFiles = 8;
		while (totalFiles > 1) {
			int file = 0;
			for (int i = 0; i < totalFiles; i += 2) {
				File pf = new File("B:/Projects/AAC_Shreyam/AAC_Shreyam/mergeSort/src/" + i + ".txt");
				File cf = new File("B:/Projects/AAC_Shreyam/AAC_Shreyam/mergeSort/src/" + (i + 1) + ".txt");
				// data.addAll(Files);
				integers.addAll(readIntermediateFile(i));
				integers.addAll(readIntermediateFile(i + 1));
				cf.delete();
				pf.delete();
				if (selection == 0)
					insertion(integers);
				else if (selection == 1)
					mergesort(integers);
				else if (selection == 2)
					quickSort(integers);
				pf = new File("B:/Projects/AAC_Shreyam/AAC_Shreyam/mergeSort/src/" + file + ".txt");
				pf.createNewFile();
				FileWriter fw = new FileWriter(pf);
				for (int j = 0; j < integers.size(); j++) {
					fw.write(integers.get(j) + "\n");
				}
				System.out.println("sorted File " + file + " created");
				file++;
				integers.clear();
				fw.close();
			}
			totalFiles /= 2;
		}
		long duration = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start);
		System.out.println("Time take is " + duration + " ms");
	}

	public static void createAndWriteInitFile() {
		File f = new File("B:/Projects/AAC_Shreyam/AAC_Shreyam/mergeSort/src/unsort.txt");
		try {
			f.createNewFile();
			FileWriter fw = new FileWriter(f);
			Random r = new Random();
			int total = 400000;
			for (int i = 0; i < total; i++) {
				int numb = r.nextInt(total);
				if (f.exists()) {
					fw.write(String.valueOf(numb) + (i < total - 1 ? "\n" : ""));
				}
			}
			System.out.println("400000 elements created");
			fw.close();
			Scanner sc = new Scanner(f);
			int countInFile = 50000;
			int noFiles = total / countInFile;
			for (int i = 0; i < noFiles; i++) {
				File nf = new File("B:/Projects/AAC_Shreyam/AAC_Shreyam/mergeSort/src/" + i + ".txt");
				nf.createNewFile();
				// FileReader fr = new FileReader(f);
				// int[] numbers = new int[50000];
				// String data = null;
				FileWriter nfw = new FileWriter(nf);
				for (int j = 0; j < countInFile; j++) {
					if (sc.hasNext()) {
						String s = sc.nextLine();
						if (nf.exists()) {
							nfw.write(s + (j < countInFile - 1 ? "\n" : ""));
						}
					}
				}
				System.out.println("File " + i + " created!");
				nfw.close();
				// br.close();
			}
			sc.close();
		} catch (Exception ex) {
			System.out.print(ex);
		}
	}

	public static ArrayList<Integer> readIntermediateFile(int val) {
		ArrayList<Integer> integers = new ArrayList<Integer>();
		List<String> files = new ArrayList<String>();
		try {
			files = Files.readAllLines(Paths.get("B:/Projects/AAC_Shreyam/AAC_Shreyam/mergeSort/src/" + val + ".txt"));
			for (String var : files) {
				integers.add(Integer.valueOf(var));
			}
			return integers;
		} catch (Exception ex) {

		}
		return integers;
	}

	public static void insertion(ArrayList<Integer> values) {
		for (int i = 1; i < values.size(); i++) {
			if (Integer.parseInt(values.get(i).toString()) < Integer.parseInt(values.get(i - 1).toString())) {
				// System.out.println(values.get(i)+"lesser than"+ values.get(i-1));
				int insert = (values.remove(i)).intValue();
				for (int j = 0; j < i; j++) {
					boolean res = false;
					if (((Integer) values.get(j)).intValue() > insert) {
						values.add(j, new Integer(insert));
						res = true;
					}
					if (res)
						break;
				}
			}
		}
		System.out.println(values.size() + " values sorted");
	}

	public static void mergesort(ArrayList<Integer> values) {
		sort(values, 0, values.size() - 1);
		System.out.println(values.size() + " values sorted");
	}

	public static void sort(ArrayList<Integer> values, int l, int r) {
		if (l < r) {
			int m = (l + r) / 2;
			sort(values, l, m);
			sort(values, m + 1, r);
			merge(values, l, m, r);
		}
	}

	public static void merge(ArrayList<Integer> values, int l, int m, int r) {
		try {
			ArrayList<Integer> a = new ArrayList<>();
			ArrayList<Integer> b = new ArrayList<>();
			for (int i = 0; i < m - l + 1; i++)
				a.add(values.get(l + i));
			for (int j = 0; j < r - m; j++)
				b.add(values.get(m + 1 + j));
			int i = 0, j = 0, k = l;
			while (i < a.size() && j < b.size()) {
				int a1 = a.get(i);
				int b1 = b.get(j);
				if (a1 < b1) {
					values.set(k, a1);
					i++;
				} else {
					values.set(k, b1);
					j++;
				}
				k++;
			}
			while (i < a.size()) {
				values.set(k, a.get(i));
				i++;
				k++;
			}
			while (j < b.size()) {
				values.set(k, b.get(j));
				j++;
				k++;
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	public static void quickSort(ArrayList<Integer> values) {
		sortQ(values, 0, values.size() - 1);
		System.out.println(values.size() + " values sorted");
	}

	public static int partition(ArrayList<Integer> values, int low, int high) {
		int pivot = values.get(high);
		int i = low - 1;
		for (int j = low; j < high; j++) {
			if (values.get(j) < pivot) {
				i++;
				int num1 = values.get(i);
				int num2 = values.get(j);
				values.set(i, num2);
				values.set(j, num1);
			}
		}
		values.remove(high);
		i += 1;
		values.add(i, pivot);
		;
		return i;
	}

	public static void sortQ(ArrayList<Integer> values, int low, int high) {
		Stack<Integer> s = new Stack<Integer>();
		s.push(low);
		s.push(high);
		while (!s.isEmpty()) {
			high = s.pop();
			low = s.pop();
			int pi = partition(values, low, high);
			if (pi - 1 > low) {
				s.push(low);
				s.push(pi - 1);
			}
			if (pi + 1 < high) {
				s.push(pi + 1);
				s.push(high);
			}
		}
	}
}