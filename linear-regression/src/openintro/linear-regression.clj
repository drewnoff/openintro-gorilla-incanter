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

;; **
;;; ## The data
;; **

;; **
;;; Let's load up the data for the 2011 season.
;; **

;; @@
(def mlb11 (iio/read-dataset "../data/mlb11.csv" :header true))
(print (i/head mlb11))
;; @@
;; ->
;;; 
;;; |               :team | :runs | :at_bats | :hits | :homeruns | :bat_avg | :strikeouts | :stolen_bases | :wins | :new_onbase | :new_slug | :new_obs |
;;; |---------------------+-------+----------+-------+-----------+----------+-------------+---------------+-------+-------------+-----------+----------|
;;; |       Texas Rangers |   855 |     5659 |  1599 |       210 |    0.283 |         930 |           143 |    96 |        0.34 |      0.46 |      0.8 |
;;; |      Boston Red Sox |   875 |     5710 |  1600 |       203 |     0.28 |        1108 |           102 |    90 |       0.349 |     0.461 |     0.81 |
;;; |      Detroit Tigers |   787 |     5563 |  1540 |       169 |    0.277 |        1143 |            49 |    95 |        0.34 |     0.434 |    0.773 |
;;; |  Kansas City Royals |   730 |     5672 |  1560 |       129 |    0.275 |        1006 |           153 |    71 |       0.329 |     0.415 |    0.744 |
;;; | St. Louis Cardinals |   762 |     5532 |  1513 |       162 |    0.273 |         978 |            57 |    90 |       0.341 |     0.425 |    0.766 |
;;; |       New York Mets |   718 |     5600 |  1477 |       108 |    0.264 |        1085 |           130 |    77 |       0.335 |     0.391 |    0.725 |
;;; |    New York Yankees |   867 |     5518 |  1452 |       222 |    0.263 |        1138 |           147 |    97 |       0.343 |     0.444 |    0.788 |
;;; |   Milwaukee Brewers |   721 |     5447 |  1422 |       185 |    0.261 |        1083 |            94 |    96 |       0.325 |     0.425 |     0.75 |
;;; |    Colorado Rockies |   735 |     5544 |  1429 |       163 |    0.258 |        1201 |           118 |    73 |       0.329 |      0.41 |    0.739 |
;;; |      Houston Astros |   615 |     5598 |  1442 |        95 |    0.258 |        1164 |           118 |    56 |       0.311 |     0.374 |    0.684 |
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; **
;;; In addition to runs scored, there are seven traditionally used variables in the 
;;; data set: at-bats, hits, home runs, batting average, strikeouts, stolen bases, 
;;; and wins. There are also three newer variables: on-base percentage, slugging 
;;; percentage, and on-base plus slugging. For the first portion of the analysis 
;;; we'll consider the seven traditional variables. At the end of the lab, you'll 
;;; work with the newer variables on your own.
;; **

;; **
;;; *1.  What type of plot would you use to display the relationship between `runs` 
;;;     and one of the other numerical variables? Plot this relationship using the 
;;;     variable `at_bats` as the predictor. Does the relationship look linear? If 
;;;     you knew a team's `at_bats`, would you be comfortable using a linear model 
;;;     to predict the number of runs?*
;; **

;; **
;;; If the relationship looks linear, we can quantify the strength of the
;;; relationship with the correlation coefficient.
;; **

;; @@
(s/correlation (i/$ :runs mlb11) (i/$ :at_bats mlb11))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-double'>0.610627046720667</span>","value":"0.610627046720667"}
;; <=

;; @@
(chart-view
  (c/scatter-plot (i/$ :at_bats mlb11) (i/$ :runs mlb11)
                  :x-label "Number of at bats"
                  :y-label "Number of runs"))
;; @@
;; =>
;;; {"type":"html","content":"<img src=\"data:image/PNG;base64,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\"/>","value":"#incanter_gorilla.render.ChartView{:content #object[org.jfree.chart.JFreeChart 0x64fb138f \"org.jfree.chart.JFreeChart@64fb138f\"], :opts nil}"}
;; <=

;; @@

;; @@
