package org;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Automation {
    public static void main(String[] args) throws InterruptedException {


        int like = 0;
        int dislike = 0;
        int matchCounter =0;

        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
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
                //The below condition is necessary in case of there is a match then will help to continue for swapping
                if (isElementPresent(driver))
                {
                    matchCounter++;
                WebElement element = driver.findElement(By.xpath("(//span[@class='action text-break-words'])[2]"));
                element.click();
                }

                boolean result = checkPercentage(likePercentage );
                randomDelay();
                if(result)
                {
                    try {
                        driver.findElement(By.xpath("//span[@data-qa-icon-name='floating-action-yes']")).click();
                        like++;
                    }
                    catch (org.openqa.selenium.StaleElementReferenceException ex)
                    {
                        driver.findElement(By.xpath("//span[@data-qa-icon-name='floating-action-yes']")).click();
                        like++;
                    }
                }
                else
                {
                    try {
                        driver.findElement(By.xpath("//span[@data-qa-icon-name='floating-action-no']")).click();
                        dislike ++;
                }
                catch (org.openqa.selenium.StaleElementReferenceException ex)
                {
                    driver.findElement(By.xpath("//span[@data-qa-icon-name='floating-action-no']")).click();
                    dislike ++;
                }
                }

                System.out.println("You liked "+ like+" and disliked "+dislike + "Cards and until now "+matchCounter +" time the system clicked continue swapping");
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
        int delay = random.nextInt(400); // Generates a random number between 500 and 2000
        Thread.sleep(delay);
    }
    public static boolean isElementPresent(WebDriver driver) {
        try {
            driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
            driver.findElement(By.xpath("(//span[@class='action text-break-words'])[2]"));
            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }
}
