package tutorial.tutorial;

import java.io.IOException;
import java.nio.file.Paths;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class Searcher {

   IndexSearcher indexSearcher;
   QueryParser queryParser;
   Query query;

   // indexSearcher, queryParserなどを初期化
   public Searcher(String indexDirectoryPath) throws IOException {
      Directory indexDirectory = FSDirectory.open(Paths.get(indexDirectoryPath));

      // IndexSearcher
      IndexReader reader = DirectoryReader.open(indexDirectory);
      indexSearcher = new IndexSearcher(reader);

      // queryParser
      queryParser = new QueryParser("contents", new StandardAnalyzer());
   }

   // クエリをパースしてsearchの実行
   public TopDocs search(String searchQuery) throws IOException, ParseException {
      query = queryParser.parse(searchQuery);
      return indexSearcher.search(query, 10);
   }

   // documentを取得
   public Document getDocument(ScoreDoc scoreDoc) throws CorruptIndexException, IOException {
      return indexSearcher.doc(scoreDoc.doc);
   }
}
