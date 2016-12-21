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

		// 支店定義ファイル
		File branchfileIn = new File(args[0], "blanch.lst");

		// 商品定義ファイル
		File commodityfileIn = new File(args[0], "commodity.lst");

		// ディレクトリ格納ファイル名
		File dir = new File(args[0]);
		File Namefiles[] = dir.listFiles();

		String str;

		// 出力処理（支店定義）
		File branchfileOut = new File(args[0], "branch.out");

		// 出力処理（商品定義）
		File commodityfileOut = new File(args[0], "commodity.out");

		// 処理1
		// existsはフォルダの存在があるかないかを確認するメソッド
		if (!branchfileIn.exists()) {
			System.out.println("支店定義ファイルは存在しません");
			return;
		}

		// Hashmapを宣言する
		HashMap<String, String> branchNameMap = new HashMap<String, String>();

		// 集計3-2で使用する変数
		HashMap<String, Long> branchSalesMap = new HashMap<String, Long>();

		BufferedReader br = null;

		try {
			FileReader fr = new FileReader(branchfileIn);
			br = new BufferedReader(fr);
			while ((str = br.readLine()) != null) {
				String[] array = str.split(",");

				if (array.length != 2 && (array[0].matches("^\\d{3}"))) {

					System.out.println("支店定義ファイルのファーマットが不正です");
					return;
				}

				// 代入
				branchNameMap.put(array[0], array[1]);
				branchSalesMap.put(array[0], 0L);
			}

		} catch (IOException e) {
			System.out.println("予期せぬエラーが発生しました");
			return;

		} finally {
			try {
				if (br != null) {
					br.close();
				}
			} catch (IOException e) {
				System.out.println("予期せぬエラーが発生しました");
				return;
			}
		}

		// 処理2
		if (!commodityfileIn.exists()) {
			System.out.println("商品定義ファイルは存在しません");
			return;
		}

		HashMap<String, String> commodityNameMap = new HashMap<String, String>();
		HashMap<String, Long> commoditySalesMap = new HashMap<String, Long>();

		try {
			FileReader fr = new FileReader(commodityfileIn);
			br = new BufferedReader(fr);
			while ((str = br.readLine()) != null) {
				String array[] = str.split(",");

				if (array.length != 2) {
					System.out.println("商品定義ファイルのファーマットが不正ですa");
					return;
				}

				if (!array[0].matches("^[0-9A-Z]{8}$")) {
					System.out.println("商品定義ファイルのファーマットが不正です");
					return;
				}

				commodityNameMap.put(array[0], array[1]);
				commoditySalesMap.put(array[0], 0L);
			}

		} catch (IOException a) {
			System.out.println("予期せぬエラーが発生しましたa");
			return;
		} finally {
			try {
				if (br != null) {
					br.close();
				}
			} catch (IOException e) {
				System.out.println("予期せぬエラーが発生しました");
				return;
			}
		}
		ArrayList<File> rcdFaileList = new ArrayList<File>();
		// ArrayListに1つずつ格納処理

		// for (File f : Namefiles) {
		for (int i = 0; i < Namefiles.length; i++) {
			if (Namefiles[i].isFile()) {
				// System.out.println("[F]" + Namefiles[i].getName());
			}
			if (Namefiles[i].getName().matches("^\\d{8}.rcd$")) {
				rcdFaileList.add(Namefiles[i]);
				//System.out.println(rcdFaileList);
			}
		}

		/*
		 * rcdファイルかどうかの判定 if (f.getName().matches("^\\d{8}.rcd$")) {
		 * rcdFaileList.add(f);
		 */

		/*
		 * ■連番チェック 「1つめのデータ」と「2つめのデータ」を比較した場合、
		 * 「2つめのデータ」に対して「1つめのデータ」の差分が「1小さい」場合は正常、 差分が「1小さい」以外の場合はエラーを表示する。
		 */

		for (int i = 0; i < rcdFaileList.size(); i++) {
			String st = rcdFaileList.get(i).getName();

			String ss = st.substring(0, 8);

			int Serial = Integer.parseInt(ss);

			//System.out.println(Serial);
			if (i != Serial - 1) {
				System.out.println("売り上げファイル名が連番になっていません");
			}
		}

		// 処理3
		// 正規表現で取り出したものを取り出す処理
		for (File f : rcdFaileList) {
			// System.out.println(f);

			try {
				FileReader fr = new FileReader(f);
				br = new BufferedReader(fr);

				ArrayList<String> codelist = new ArrayList<String>();

				while ((str = br.readLine()) != null) {
					codelist.add(str);


					// ------------------------------------
					if (!branchSalesMap.containsKey(codelist.get(0))) {

						System.out.println(branchSalesMap.entrySet());
						System.out.println(codelist.get(0));
						System.out.println("支店コードが不正です");
						return;
					}
//					 if (!commoditySalesMap.containsKey(codelist.get(1))) {
//					 System.out.println(commoditySalesMap.entrySet());
//					 System.out.println("商品コードが不正です");
//					 return;
//					 }

					if (codelist.size() != 3) {

						System.out.println("フォーマットが不正です");
						return;
					}
					// ------------------------------------
				}
				long rcdValue = Long.parseLong(codelist.get(2));

				if (rcdValue > 9999999999L) {
					System.out.println("合計金額が10桁を超えました");
					return;

				}

				long branchVal = branchSalesMap.get(codelist.get(0)) + rcdValue;

				if (branchVal > 9999999999L) {
					System.out.println("合計金額が10桁を超えました");
					return;
				}

				long commodityVal = commoditySalesMap.get(codelist.get(1)) + rcdValue;

				if (commodityVal > 9999999999L) {
					System.out.println("合計金額が10桁を超えました");
					return;
				}

				branchSalesMap.put(codelist.get(0), branchVal);
				commoditySalesMap.put(codelist.get(1), commodityVal);

			} catch (IOException e) {
				System.out.println("予期せぬエラーが発生しました");
			} finally {
				try {
					if (br != null) {
						br.close();
					}
				} catch (IOException e) {
					System.out.println("予期せぬエラーが発生しました");
				}

			}

		}

		// 降順に並べ替え List 生成 (ソート用)
		// 支店定義出力
		List<Map.Entry<String, Long>> branchEntries = new ArrayList<Map.Entry<String, Long>>(branchSalesMap.entrySet());
		Collections.sort(branchEntries, new Comparator<Map.Entry<String, Long>>() {

			@Override
			public int compare(Entry<String, Long> entry1, Entry<String, Long> entry2) {

				// 自動生成されたメソッド・スタブ
				return ((Long) entry2.getValue()).compareTo((Long) entry1.getValue());

			}
		});

		// 内容を表示
		for (Entry<String, Long> s : branchEntries) {
			//System.out.println(s.getKey() + "," + branchNameMap.get(s.getKey()) + "," + s.getValue());
		}

		try {
			FileWriter fi = new FileWriter(branchfileOut);
			BufferedWriter bw = new BufferedWriter(fi);

			String separator = System.getProperty("line.separator");

			for (Entry<String, Long> s : branchEntries) {
				bw.write((s.getKey() + "," + branchNameMap.get(s.getKey()) + "," + s.getValue()) + separator);
			}
			bw.close();

		} catch (IOException e) {
			System.out.println("予期せぬエラーが発生しました");
		}

		// 商品定義出力
		List<Map.Entry<String, Long>> commodityEntries = new ArrayList<Map.Entry<String, Long>>(
				commoditySalesMap.entrySet());
		Collections.sort(commodityEntries, new Comparator<Map.Entry<String, Long>>() {

			@Override
			public int compare(Entry<String, Long> entry1, Entry<String, Long> entry2) {

				// 自動生成されたメソッド・スタブ
				return ((Long) entry2.getValue()).compareTo((Long) entry1.getValue());

			}
		});
		// 内容を表示

		for (Entry<String, Long> s : commodityEntries) {
			//System.out.println(s.getKey() + "," + commodityNameMap.get(s.getKey()) + "," + s.getValue());
		}

		try {
			FileWriter fi = new FileWriter(commodityfileOut);
			BufferedWriter bw = new BufferedWriter(fi);

			String separator = System.getProperty("line.separator");

			for (Entry<String, Long> s : commodityEntries) {
				bw.write((s.getKey() + "," + commodityNameMap.get(s.getKey()) + "," + s.getValue()) + separator);
			}
			bw.close();

		} catch (IOException e) {
			System.out.println("予期せぬエラーが発生しました");
		}

	}
}
