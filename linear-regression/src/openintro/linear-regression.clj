;; gorilla-repl.fileformat = 1

;; **
;;; # Introduction to linear regression
;;; 
;;; Original lab is available [here](http://htmlpreview.github.io/?https://github.com/andrewpbray/oiLabs-base-R/blob/master/simple_regression/simple_regression.html).
;;; You can download the data for this lab from [here](https://www.openintro.org/stat/data/?data=mlb11). 
;; **

;; @@
(ns openintro.linear-regression)

(require '[incanter.core :as i]
         '[incanter.charts :as c]
         '[incanter.io :as iio]
         '[incanter.stats :as s]
         '[incanter.distributions :as dist])
(use 'incanter-gorilla.render :reload)
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; **
;;; ## Batter up 
;;; 
;;; The movie [Moneyball](http://en.wikipedia.org/wiki/Moneyball_(film)) focuses on
;;; the "quest for the secret of success in baseball". It follows a low-budget team, 
;;; the Oakland Athletics, who believed that underused statistics, such as a player's 
;;; ability to get on base, betterpredict the ability to score runs than typical 
;;; statistics like home runs, RBIs (runs batted in), and batting average. Obtaining 
;;; players who excelled in these underused statistics turned out to be much more 
;;; affordable for the team.
;;; 
;;; In this lab we'll be looking at data from all 30 Major League Baseball teams and
;;; examining the linear relationship between runs scored in a season and a number 
;;; of other player statistics. Our aim will be to summarize these relationships 
;;; both graphically and numerically in order to find which variable, if any, helps 
;;; us best predict a team's runs scored in a season.
;; **

;; @@

;; @@
