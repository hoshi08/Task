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
		File blanchfileIn = new File(args[0], "blanch.lst");

		// 商品定義ファイル
		File commodityfileIn = new File(args[0], "commodity.lst");

		// ディレクトリ格納ファイル名
		File dir = new File(args[0]);
		File Namefiles[] = dir.listFiles();

		String str;

		// 出力処理（支店定義）
		File blanchfileOut = new File(args[0], "branch.out");

		// 出力処理（商品定義）
		File commodityfileOut = new File(args[0], "commodity.out");

		// 処理1
		// existsはフォルダの存在があるかないかを確認するメソッド
		if (!blanchfileIn.exists()) {
			System.out.println("支店定義ファイルは存在しません");
			return;
		}

		// Hashmapを宣言する
		HashMap<String, String> branchNameMap = new HashMap<String, String>();

		// 集計3-2で使用する変数
		HashMap<String, Long> branchCodeMap = new HashMap<String, Long>();

		BufferedReader br = null;

		try {
			FileReader fr = new FileReader(blanchfileIn);
			br = new BufferedReader(fr);
			while ((str = br.readLine()) != null) {
				String array[] = str.split(",");

				if (array.length != 2) {
					System.out.println("支店定義ファイルのファーマットが不正です");
					return;
				}

				if (!array[0].matches("^\\d{3}")) {
					System.out.println("支店定義ファイルのファーマットが不正です");
					return;
				}

				// 代入
				branchNameMap.put(array[0], array[1]);
				branchCodeMap.put(array[0], 0L);
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
		HashMap<String, Long> commodityCodeMap = new HashMap<String, Long>();

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
				commodityCodeMap.put(array[0], 0L);
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

		// 処理3
		ArrayList<File> rcdFaileList = new ArrayList<File>();

		// ArrayListに1つずつ格納処理
		for (File f : Namefiles) {
			// rcdファイルかどうかの判定
			if (f.getName().matches("^\\d{8}.rcd$")) {
				rcdFaileList.add(f);
				 // 連番チェック
				 for(int i = 0; );

				 (rcdFaileList Current =  );{




							 while ((Current = rcdFaileList.readLine()) != null) {
				 }
				 System.out.println(fi);

			}

		}

		// 正規表現で取り出したものを取り出す処理
		for (File f :rcdFaileList) {
			// System.out.println(f);

			try {
				FileReader fr = new FileReader(f);
				br = new BufferedReader(fr);

				ArrayList<String> codelist = new ArrayList<String>();

				while ((str = br.readLine()) != null) {
					codelist.add(str);
				}

				long rcdValue = Long.parseLong(codelist.get(2));

				long branchVal = branchCodeMap.get(codelist.get(0)) + rcdValue;

				long commodityVal = commodityCodeMap.get(codelist.get(1)) + rcdValue;

				branchCodeMap.put(codelist.get(0), branchVal);
				commodityCodeMap.put(codelist.get(1), commodityVal);

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
		List<Map.Entry<String, Long>> branchEntries = new ArrayList<Map.Entry<String, Long>>(
				branchCodeMap.entrySet());
		Collections.sort(branchEntries, new Comparator<Map.Entry<String, Long>>() {

			@Override
			public int compare(Entry<String, Long> entry1, Entry<String, Long> entry2) {

				// 自動生成されたメソッド・スタブ
				return ((Long) entry2.getValue()).compareTo((Long) entry1.getValue());

			}
		});

		// 内容を表示
		for (Entry<String, Long> s : branchEntries) {
			System.out.println(s.getKey() + "," + branchNameMap.get(s.getKey()) + "," + s.getValue());
		}

		try {
			FileWriter fi = new FileWriter(blanchfileOut);
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
				commodityCodeMap.entrySet());
		Collections.sort(commodityEntries, new Comparator<Map.Entry<String, Long>>() {

			@Override
			public int compare(Entry<String, Long> entry1, Entry<String, Long> entry2) {

				// 自動生成されたメソッド・スタブ
				return ((Long) entry2.getValue()).compareTo((Long) entry1.getValue());

			}
		});
		// 内容を表示

		for (Entry<String, Long> s : commodityEntries) {
			System.out.println(s.getKey() + "," + commodityNameMap.get(s.getKey()) + "," + s.getValue());
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
