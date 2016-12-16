package jp.co.iccom.hoshi_kouji.calculate_sales;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class calculate {

	public static void main(String args[]) {

		File file = new File(args[0], "blanch.lst");
		File file1 = new File(args[0], "commodity.lst");

		File dir = new File(args[0]);
		File files[] = dir.listFiles();

		String str;


		// 処理1
		// existsはフォルダの存在があるかないかを確認するメソッド
		if (!file.exists()) {

			System.out.println("支店定義ファイルは存在しません");
		}

		// Hashmapを宣言する
		HashMap<String, String> branchname = new HashMap<String, String>();

		// 集計3-2で使用する変数
		HashMap<String, Long> brnchcodemap = new HashMap<String, Long>();

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
				brnchcodemap.put(array[0], 0L);

			}
			br.close();
		} catch (IOException e) {
			System.out.println(e);

		}

		System.out.println(branchname.entrySet());
		System.out.println(brnchcodemap.entrySet());

		// 処理2
		if (!file1.exists()) {

			System.out.println("商品定義ファイルは存在しません");

		}

		HashMap<String, String> commodity = new HashMap<String, String>();

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
				brnchcodemap.put(array[0], 0L);
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

				long branchVal = brnchcodemap.get(codelist.get(0)) + rcdValue;




				brnchcodemap.put(codelist.get(0), branchVal);
				System.out.println(branchVal);

			} catch (IOException e) {
				System.out.println(e);
			}

		}

	}
}