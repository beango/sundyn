package com.sundyn.statistics;

import java.io.*;
import com.sundyn.service.*;
import java.util.*;
import com.sundyn.vo.*;

public class AdviceStatistics implements Serializable
{
    public static List<Map<String, List<Map<String, Map<String, Double>>>>> adviceStatistics(final int startrow, final int pageSize, final AdviceService adviceService) {
        final List<AdviceVo> advices = adviceService.findAdvice(false, startrow, pageSize);
        final List<Map<String, List<Map<String, Map<String, Double>>>>> adviceStatistics = new ArrayList<Map<String, List<Map<String, Map<String, Double>>>>>();
        for (final AdviceVo vo : advices) {
            final Map<String, List<Map<String, Map<String, Double>>>> m = new HashMap<String, List<Map<String, Map<String, Double>>>>();
            final QuestionVo q = vo.getQuestion();
            final List<AnswerVo> answers = vo.getAnswers();
            final List<Map<String, Map<String, Double>>> list = new ArrayList<Map<String, Map<String, Double>>>();
            for (final AnswerVo av : answers) {
                final Map<String, Map<String, Double>> rateMap = new HashMap<String, Map<String, Double>>();
                final List<Map<String, Double>> aList = new ArrayList<Map<String, Double>>();
                final Map<String, Double> aMap = new HashMap<String, Double>();
                final double rate = adviceService.getRate(av);
                final int a = adviceService.getAnserCountByFatherid(av.getFatherid(), av.getId());
                aMap.put(new StringBuilder(String.valueOf(a)).toString(), rate);
                rateMap.put(av.getAnswer(), aMap);
                list.add(rateMap);
            }
            m.put(q.getQuestion(), list);
            adviceStatistics.add(m);
        }
        return adviceStatistics;
    }
}
