package jp.co.iccom.hoshi_kouji.calculate_sales;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class calculate {

	public static void main(String args[]) {
		//入力処理
		File file = new File(args[0], "blanch.lst");
		File file1 = new File(args[0], "commodity.lst");

		File dir = new File(args[0]);
		File files[] = dir.listFiles();

		String str;

		//出力処理（支店定義）
		File file2 = new File(args[0], "branch.out");
		//出力処理（商品定義）
		File file3 = new File(args[0], "commodity.out");



		// 処理1
		// existsはフォルダの存在があるかないかを確認するメソッド
		if (!file.exists()) {

			System.out.println("支店定義ファイルは存在しません");
		}

		// Hashmapを宣言する
		HashMap<String, String> branchname = new HashMap<String, String>();

		// 集計3-2で使用する変数
		HashMap<String, Long> branchcodemap = new HashMap<String, Long>();

		try {
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			while ((str = br.readLine()) != null) {
				String array[] = str.split(",");

				if (!array[0].matches("^\\d{3}")) {

					System.out.println("支店定義ファイルのファーマットが不正です");
				}
				// 代入
				branchname.put(array[0], array[1]);
				branchcodemap.put(array[0], 0L);

			}
			br.close();
		} catch (IOException e) {
			System.out.println(e);

		}

		// System.out.println(branchname.entrySet());
		// System.out.println(branchcodemap.entrySet());




		// 処理2
		if (!file1.exists()) {

			System.out.println("商品定義ファイルは存在しません");

		}

		HashMap<String, String> commodity = new HashMap<String, String>();

		HashMap<String, Long> commoditycodemap = new HashMap<String, Long>();

		try {
			FileReader fr = new FileReader(file1);
			BufferedReader br = new BufferedReader(fr);
			while ((str = br.readLine()) != null) {
				String array[] = str.split(",");

				if (!array[0].matches("^[0-9A-Z]{8}$") || array.length != 2) {

					System.out.println("商品定義ファイルのファーマットが不正です");
					return;
				}

				commodity.put(array[0], array[1]);
				commoditycodemap.put(array[0], 0L);

			}

			br.close();
		} catch (IOException a) {
			System.out.println(a);

		}

		// 処理3
		ArrayList<File> foo = new ArrayList<File>();

		// ArrayListに1つずつ格納処理
		for (File f : files) {
			// rcdファイルかどうかの判定
			if (f.getName().matches("^\\d{8}.rcd$")) {
				foo.add(f);
				// System.out.println(f);
			}

			// 連番チェック
			// for (File f : foo) {
			// System.out.println(f);

			// }

		}

		// 正規表現で取り出したものを取り出す処理
		for (File f : foo) {
			System.out.println(f);

			try {
				FileReader fr = new FileReader(f);
				BufferedReader br = new BufferedReader(fr);

				ArrayList<String> codelist = new ArrayList<String>();

				while ((str = br.readLine()) != null) {
					codelist.add(str);
				}

				long rcdValue = Long.parseLong(codelist.get(2));

				long branchVal = branchcodemap.get(codelist.get(0)) + rcdValue;

				long commodityVal = commoditycodemap.get(codelist.get(1)) + rcdValue;

				branchcodemap.put(codelist.get(0), branchVal);
				commoditycodemap.put(codelist.get(1), commodityVal);

				// System.out.println(branchVal);
				// System.out.println(commodityVal);
				// System.out.println(branchcodemap.entrySet());
				// System.out.println(commoditycodemap.entrySet());

			} catch (IOException e) {
				System.out.println(e);
			}
		}


		// 降順に並べ替え
		// List 生成 (ソート用)
		//支店定義出力
		List<Map.Entry<String, Long>> entries = new ArrayList<Map.Entry<String, Long>>(branchcodemap.entrySet());
		Collections.sort(entries, new Comparator<Map.Entry<String, Long>>() {

			@Override
			public int compare(Entry<String, Long> entry1, Entry<String, Long> entry2) {

				// 自動生成されたメソッド・スタブ
				return ((Long) entry2.getValue()).compareTo((Long) entry1.getValue());

			}
		});
		// 内容を表示

		for (Entry<String, Long> s : entries) {
			System.out.println(s.getKey() + ","  + branchname.get(s.getKey()) +  "," + s.getValue());
		}

		try {
			FileWriter fi = new FileWriter(file2);
			BufferedWriter bw = new BufferedWriter(fi);

			String separator = System.getProperty("line.separator");

			for (Entry<String, Long> s : entries) {
				bw.write((s.getKey() + ","  + branchname.get(s.getKey()) +  "," + s.getValue()) + separator);
			}
			bw.close();


		} catch (IOException e) {
			System.out.println(e);
		}


		//商品定義出力
				List<Map.Entry<String, Long>> entries1 = new ArrayList<Map.Entry<String, Long>>(commoditycodemap.entrySet());
				Collections.sort(entries1, new Comparator<Map.Entry<String, Long>>() {

					@Override
					public int compare(Entry<String, Long> entry1, Entry<String, Long> entry2) {

						// 自動生成されたメソッド・スタブ
						return ((Long) entry2.getValue()).compareTo((Long) entry1.getValue());

					}
				});
				// 内容を表示

				for (Entry<String, Long> s : entries1) {
					System.out.println(s.getKey() + ","  + commodity.get(s.getKey()) +  "," + s.getValue());
				}

				try {
					FileWriter fi = new FileWriter(file3);
					BufferedWriter bw = new BufferedWriter(fi);

					String separator = System.getProperty("line.separator");

					for (Entry<String, Long> s : entries1) {
						bw.write((s.getKey() + ","  + commodity.get(s.getKey()) +  "," + s.getValue()) + separator);
					}
					bw.close();


				} catch (IOException e) {
					System.out.println(e);
				}

	}
}


