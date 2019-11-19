package com.atu.util;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.corpus.document.sentence.Sentence;
import com.hankcs.hanlp.corpus.document.sentence.word.IWord;
import com.hankcs.hanlp.model.crf.CRFLexicalAnalyzer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @desciption 自然语言处理 中文分词 词性标注 命名实体识别 依存句法分析
 * 新词发现 关键词短语提取 自动摘要 文本分类聚类 拼音简繁
 * @link https://github.com/hankcs/HanLP
 */
public class HanlpUtil {

    /**
     * @param content
     * @return
     * @description 提取摘要
     */
    public static List<String> summary(String content) {
        List<String> summary = HanLP.extractSummary(content, 3);
        return summary;
    }

    /**
     * @param content
     * @return
     * @desciption 提取短语
     */
    public static List<String> phrase(String content) {
        return HanLP.extractPhrase(content, 5);
    }

    /**
     * @param document
     * @return
     * @throws IOException
     * @desciption 找出相关词性聚合成一个list
     */
    public static List<String> findWordsAndCollectByLabel(List<String> document) throws IOException {
        /* 对词性进行分析，找出合适的词性 */
        CRFLexicalAnalyzer analyzer = new CRFLexicalAnalyzer();
        Sentence analyzeWords = analyzer.analyze(String.valueOf(document));

        List<IWord> wordsByLabell = analyzeWords.findWordsByLabel("n");
        List<IWord> wordsByLabel2 = analyzeWords.findWordsByLabel("ns");
        List<IWord> wordsByLabel3 = analyzeWords.findWordsByLabel("t");
        List<IWord> wordsByLabel4 = analyzeWords.findWordsByLabel("j");
        List<IWord> wordsByLabel5 = analyzeWords.findWordsByLabel("vn");
        List<IWord> wordsByLabel6 = analyzeWords.findWordsByLabel("nr");
        List<IWord> wordsByLabel7 = analyzeWords.findWordsByLabel("nt");
        List<IWord> wordsByLabel8 = analyzeWords.findWordsByLabel("nz");

        wordsByLabell.addAll(wordsByLabel2);
        wordsByLabell.addAll(wordsByLabel3);
        wordsByLabell.addAll(wordsByLabel4);
        wordsByLabell.addAll(wordsByLabel5);
        wordsByLabell.addAll(wordsByLabel6);
        wordsByLabell.addAll(wordsByLabel7);
        wordsByLabell.addAll(wordsByLabel8);

        List<String> words = new ArrayList<>();

        for (IWord word : wordsByLabell) {
            words.add(word.getValue());
        }

        return words;
    }

    public static void main(String[] args) {
        String document = "算法可大致分为基本算法、数据结构的算法、数论算法、计算几何的算法、图的算法、动态规划以及数值分析、加密算法、排序算法、检索算法、随机化算法、并行算法、厄米变形模型、随机森林算法。\n" +
                "算法可以宽泛的分为三类，\n" +
                "一，有限的确定性算法，这类算法在有限的一段时间内终止。他们可能要花很长时间来执行指定的任务，但仍将在一定的时间内终止。这类算法得出的结果常取决于输入值。\n" +
                "二，有限的非确定算法，这类算法在有限的时间内终止。然而，对于一个（或一些）给定的数值，算法的结果并不是唯一的或确定的。\n" +
                "三，无限的算法，是那些由于没有定义终止定义条件，或定义的条件无法由输入的数据满足而不终止运行的算法。通常，无限算法的产生是由于未能确定的定义终止条件。";
        List<String> sentenceList = phrase(document);
        //  List<String> sentenceList = summary(document);
        System.out.println(sentenceList);

    }
}
