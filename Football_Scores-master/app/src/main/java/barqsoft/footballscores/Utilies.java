package barqsoft.footballscores;

import android.content.Context;

/**
 * Created by yehya khaled on 3/3/2015.
 */
public class Utilies
{
    public static final int BUNDESLIGA        = 394;
    public static final int LIGUE             = 396;
    public static final int PREMIER_LEAGUE    = 398;
    public static final int PRIMERA_DIVISION  = 399;
    public static final int SEGUNDA_DIVISION  = 400;
    public static final int SERIE_A           = 401;
    public static final int PRIMERA_LIGA      = 402;
    public static final int EREDIVISIE        = 404;
    public static final int CHAMPIONS_LEAGUE  = 405;

    private Context mContext;

    public static String getLeague(int league_num)
    {
        switch (league_num)
        {
            case SERIE_A : return MyApp.getContext().getResources().getString(R.string.seriaa);
            case PREMIER_LEAGUE : return MyApp.getContext().getResources().getString(R.string.premierleague);
            case CHAMPIONS_LEAGUE : return MyApp.getContext().getResources().getString(R.string.champions_league);
            case PRIMERA_DIVISION : return MyApp.getContext().getResources().getString(R.string.primeradivison);
            case BUNDESLIGA : return MyApp.getContext().getResources().getString(R.string.bundesliga);
            default: return MyApp.getContext().getResources().getString(R.string.unknown_league);
        }
    }
    public static String getMatchDay(int match_day,int league_num)
    {
        if(league_num == CHAMPIONS_LEAGUE)
        {
            if (match_day <= 6)
            {
                return MyApp.getContext().getResources().getString(R.string.group_stages);
            }
            else if(match_day == 7 || match_day == 8)
            {
                return MyApp.getContext().getResources().getString(R.string.first_knockout_round);
            }
            else if(match_day == 9 || match_day == 10)
            {
                return MyApp.getContext().getResources().getString(R.string.quarter_final);
            }
            else if(match_day == 11 || match_day == 12)
            {
                return MyApp.getContext().getResources().getString(R.string.semi_final);
            }
            else
            {
                return MyApp.getContext().getResources().getString(R.string.final_text);
            }
        }
        else
        {
            return MyApp.getContext().getResources().getString(R.string.match_day) + String.valueOf(match_day);
        }
    }

    public static String getScores(int home_goals,int awaygoals)
    {
        if(home_goals < 0 || awaygoals < 0)
        {
            return " - ";
        }
        else
        {
            return String.valueOf(home_goals) + " - " + String.valueOf(awaygoals);
        }
    }

    public static int getTeamCrestByTeamName (String teamname)
    {
        if (teamname==null){return R.drawable.no_icon;}
        switch (teamname)
        { //This is the set of icons that are currently in the app. Feel free to find and add more
            //as you go.
            case "Arsenal London FC" : return R.drawable.arsenal;
            case "Manchester United FC" : return R.drawable.manchester_united;
            case "Swansea City" : return R.drawable.swansea_city_afc;
            case "Leicester City" : return R.drawable.leicester_city_fc_hd_logo;
            case "Everton FC" : return R.drawable.everton_fc_logo1;
            case "West Ham United FC" : return R.drawable.west_ham;
            case "Tottenham Hotspur FC" : return R.drawable.tottenham_hotspur;
            case "West Bromwich Albion" : return R.drawable.west_bromwich_albion_hd_logo;
            case "Sunderland AFC" : return R.drawable.sunderland;
            case "Stoke City FC" : return R.drawable.stoke_city;
            default: return R.drawable.no_icon;
        }
    }
}
