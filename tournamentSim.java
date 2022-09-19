import java.util.ArrayList;
import java.util.Scanner;

public class tournamentSim {

    public static void main(String[] args)
    {
        // Select the number of teams and all team names
        int participateCount = participantNum();
        ArrayList<teams> participants = new ArrayList<>(participantList(participateCount));
        int round = 1;

        while(participants.size()>1) // Repeat until there is only one team left
        {
            // Generate match-ups
            ArrayList<teams> home = new ArrayList<>();
            ArrayList<teams> away = new ArrayList<>();

            home = fixtures(home, participants, participants.size() / 2);
            away = fixtures(away, participants, 0);
            print("Round " + round + ":");
            displayFixture(home, away);
            print("");
            String proceed = inputStr("Press enter to continue");

            // Simulate results
            for (int i = 0; i < home.size(); i++)
            {
                String result = simulateResult(home.get(i).rating, away.get(i).rating);
                if (result == "home") // Home team wins
                {
                    participants.add(home.get(i));
                    print(home.get(i).name);
                }
                else // Away team wins
                {
                    participants.add(away.get(i));
                    print(away.get(i).name);
                }
            }
            proceed = inputStr("Press enter to continue");
            round++;
        }

        print("The winner of the tournament is: " + participants.get(0).name);
    }

    public static int participantNum() // Select the number of participants
    {
        int participateCount = inputInt("How many teams would you like? ");
        while(!(participateCount == 4 | participateCount == 8 | participateCount == 16 | participateCount == 32 | participateCount == 64))
        { // Make sure that the tournament can be completed
            participateCount = inputInt("Please select one of the following numbers: 4, 8, 16, 32 or 64" +
                    "");
        }
        return participateCount;
    }

    public static ArrayList<teams> participantList(int num) // Select participant names and rating
    {
        ArrayList<teams> list = new ArrayList<>();
        for (int i=0; i<num; i++)
        {
            int number = i+1;
            String name = inputStr("Name of participant number " + number + ": ");
            int rating = inputInt("What is the rating of " + name + "?");
            teams newTeam = new teams(name, rating);
            list.add(newTeam);
        }
        return list;
    }

    public static ArrayList<teams> fixtures(ArrayList<teams> match, ArrayList<teams> participants, int num)
    {
        while (participants.size() > num)
        {
            int index = getRandomNumber(0, participants.size()-1);
            teams team1 = participants.get(index);
            match.add(team1);
            participants.remove(index);
        }
        return match;
    }

    public static String simulateResult(int homeRating, int awayRating)
    {
        ArrayList<String> hat = new ArrayList<>();
        for (int i=0; i<homeRating; i++)
        {
            hat.add("home");
        }
        for (int i=0; i<awayRating; i++)
        {
            hat.add("away");
        }

        int result = getRandomNumber(0, hat.size()-1);
        return hat.get(result);
    }

    public static void displayFixture(ArrayList<teams> home, ArrayList<teams> away)
    {
        for (int i=0; i < home.size(); i++)
        {
            print(home.get(i).name + " vs " + away.get(i).name);
        }
    }

    public static int getRandomNumber(int Min, int Max) // Select random number
    {
        return Min + (int)(Math.random() * ((Max - Min) + 1));
    }

    public static String inputStr(String message) // Take string input from the user
    {
        String answer;
        Scanner scanner = new Scanner(System.in);
        System.out.println(message);
        answer = scanner.nextLine();
        return answer;
    }

    public static int inputInt(String message) // Take integer input from the user
    {
        int answer;
        Scanner scanner = new Scanner(System.in);
        print(message);
        answer = scanner.nextInt();
        return answer;
    }

    public static void print(String message) // Output message to console
    {
        System.out.println(message);
    }
}
