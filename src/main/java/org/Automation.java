package org;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Automation {
    public static void main(String[] args) throws InterruptedException {


        int like = 0;
        int dislike = 0;
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        driver.get("https://bumble.com/get-started");
        driver.findElement(By.xpath("(//span[@class='action text-break-words'])[2]")).click();
        Scanner myObj = new Scanner(System.in);
        System.out.println("Please input your phone number without using country code:");
        String userName = myObj.nextLine();
        driver.findElement(By.xpath("//input[@name='phone']")).sendKeys(userName);
        driver.findElement(By.xpath("(//span[@class='action text-break-words'])[2]")).click();
        System.out.println("Please input your 6 digit verification code:");
        userName = myObj.nextLine();
        for(int i = 1;i<=6;i++)
        {
            char[] code = userName.toCharArray();
            driver.findElement(By.xpath("(//input)["+i+"]")).sendKeys(String.valueOf(code[i-1]));
        }
        driver.findElement(By.xpath("(//div[@class='button-group__item'])[2]"));
        System.out.println("How many percentage of the cards do you prefer to like?");
        System.out.println("Please input the integer number between 0-100");
        int likePercentage = Integer.parseInt(myObj.nextLine());
        if (likePercentage < 0 || likePercentage > 100) {
            System.out.println("Please enter a valid integer between 0 and 100.");
        } else {
            while (true)
            {
                boolean result = checkPercentage(likePercentage );
                randomDelay();
                if(result)
                {
                    driver.findElement(By.xpath("//span[@data-qa-icon-name='floating-action-yes']")).click();
                    like ++;
                }
                else
                {
                    driver.findElement(By.xpath("//span[@data-qa-icon-name='floating-action-no']")).click();
                    dislike ++;
                }

                System.out.println("You liked "+ like+" and disliked "+dislike + "Cards");
            }
        }




    }

    public static boolean checkPercentage(int percentage) {
        Random random = new Random();
        int randomNumber = random.nextInt(100) + 1; // Generates a number between 1 and 100
        return randomNumber <= percentage;
    }

    public static void randomDelay() throws InterruptedException {
        Random random = new Random();
        int delay = 500 + random.nextInt(1500); // Generates a random number between 500 and 2000
        Thread.sleep(delay);
    }
}
