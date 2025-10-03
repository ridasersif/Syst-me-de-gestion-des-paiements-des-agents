package controller;

import service.PaymentStatisticsService;

public class StatisticsController {

    private final PaymentStatisticsService statisticsService;

    public StatisticsController(PaymentStatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    public void showAgentStatistics(int agentId) {
        String report = statisticsService.getAgentStatistics(agentId);
        System.out.println(report);
    }
}
