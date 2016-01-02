;; gorilla-repl.fileformat = 1

;; **
;;; # Introduction to data
;;; 
;;; Original lab is available [here](http://htmlpreview.github.io/?https://github.com/andrewpbray/oiLabs-base-R/blob/master/intro_to_data/intro_to_data.html).
;;; You can download the data for this lab from [here](https://www.openintro.org/stat/data/?data=cdc). 
;; **

;; @@
(ns openintro.intro-to-data)

(require '[incanter.core :as i]
         '[incanter.charts :as c]
         '[incanter.io :as iio]
         '[incanter.stats :as s])
(use 'incanter-gorilla.render :reload)
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; **
;;; ## Getting started
;;; The Behavioral Risk Factor Surveillance System (BRFSS) is an annual telephone 
;;; survey of 350,000 people in the United States. As its name implies, the BRFSS 
;;; is designed to identify risk factors in the adult population and report 
;;; emerging health trends. For example, respondents are asked about their diet and 
;;; weekly physical activity, their HIV/AIDS status, possible tobacco use, and even
;;; their level of healthcare coverage. The BRFSS Web site 
;;; ([http://www.cdc.gov/brfss](http://www.cdc.gov/brfss)) contains a complete 
;;; description of the survey, including the research questions that motivate the 
;;; study and many interesting results derived from the data.
;;; 
;;; We will focus on a random sample of 20,000 people from the BRFSS survey 
;;; conducted in 2000. While there are over 200  variables in this data set, we will
;;; work with a small subset.
;;; 
;;; We begin by loading the data set of 20,000 observations into Incanter dataset. 
;;; TODO: a bit of explanation about loading the data, csv format? :header true option.
;; **

;; @@
(def cdc (iio/read-dataset "../data/cdc.csv" :header true))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;openintro.intro-to-data/cdc</span>","value":"#'openintro.intro-to-data/cdc"}
;; <=

;; **
;;; TODO: a bit of explanation about Incanter dataset.
;;; To view the names of the columns, type the command
;; **

;; @@
(i/col-names cdc)
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:genhlth</span>","value":":genhlth"},{"type":"html","content":"<span class='clj-keyword'>:exerany</span>","value":":exerany"},{"type":"html","content":"<span class='clj-keyword'>:hlthplan</span>","value":":hlthplan"},{"type":"html","content":"<span class='clj-keyword'>:smoke100</span>","value":":smoke100"},{"type":"html","content":"<span class='clj-keyword'>:height</span>","value":":height"},{"type":"html","content":"<span class='clj-keyword'>:weight</span>","value":":weight"},{"type":"html","content":"<span class='clj-keyword'>:wtdesire</span>","value":":wtdesire"},{"type":"html","content":"<span class='clj-keyword'>:age</span>","value":":age"},{"type":"html","content":"<span class='clj-keyword'>:gender</span>","value":":gender"}],"value":"[:genhlth :exerany :hlthplan :smoke100 :height :weight :wtdesire :age :gender]"}
;; <=

;; **
;;; This returns the names `genhlth`, `exerany`, `hlthplan`, `smoke100`, `height`, 
;;; `weight`, `wtdesire`, `age`, and `gender`. Each one of these variables 
;;; corresponds to a question that was asked in the survey.  For example, for 
;;; `genhlth`, respondents were asked to evaluate their general health, responding
;;; either excellent, very good, good, fair or poor. The `exerany` variable 
;;; indicates whether the respondent exercised in the past month (1) or did not (0).
;;; Likewise, `hlthplan` indicates whether the respondent had some form of health 
;;; coverage (1) or did not (0). The `smoke100` variable indicates whether the 
;;; respondent had smoked at least 100 cigarettes in her lifetime. The other 
;;; variables record the respondent's `height` in inches, `weight` in pounds as well
;;; as their desired weight, `wtdesire`, `age` in years, and `gender`.
;; **

;; **
;;; *1.  How many cases are there in this data set?  How many variables?*
;; **

;; **
;;; To identify the number of cases in this data set one could type
;; **

;; @@
(i/nrow cdc)
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-unkown'>20000</span>","value":"20000"}
;; <=

;; **
;;; and for number of variables just type
;; **

;; @@
(i/ncol cdc)
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-unkown'>9</span>","value":"9"}
;; <=

;; **
;;; We can have a look at the first few entries (rows) of our data with the command
;; **

;; @@
(print (i/head cdc))
;; @@
;; ->
;;; 
;;; |  :genhlth | :exerany | :hlthplan | :smoke100 | :height | :weight | :wtdesire | :age | :gender |
;;; |-----------+----------+-----------+-----------+---------+---------+-----------+------+---------|
;;; |      good |        0 |         1 |         0 |      70 |     175 |       175 |   77 |       m |
;;; |      good |        0 |         1 |         1 |      64 |     125 |       115 |   33 |       f |
;;; |      good |        1 |         1 |         1 |      60 |     105 |       105 |   49 |       f |
;;; |      good |        1 |         1 |         0 |      66 |     132 |       124 |   42 |       f |
;;; | very good |        0 |         1 |         0 |      61 |     150 |       130 |   55 |       f |
;;; | very good |        1 |         1 |         0 |      64 |     114 |       114 |   55 |       f |
;;; | very good |        1 |         1 |         0 |      71 |     194 |       185 |   31 |       m |
;;; | very good |        0 |         1 |         0 |      67 |     170 |       160 |   45 |       m |
;;; |      good |        0 |         1 |         1 |      65 |     150 |       130 |   27 |       f |
;;; |      good |        1 |         1 |         0 |      70 |     180 |       170 |   44 |       m |
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; **
;;; and similarly we can look at the last few by typing
;; **

;; @@
(print (i/tail cdc))
;; @@
;; ->
;;; 
;;; |  :genhlth | :exerany | :hlthplan | :smoke100 | :height | :weight | :wtdesire | :age | :gender |
;;; |-----------+----------+-----------+-----------+---------+---------+-----------+------+---------|
;;; | excellent |        1 |         1 |         0 |      71 |     195 |       190 |   43 |       m |
;;; | very good |        1 |         1 |         1 |      72 |     210 |       175 |   52 |       m |
;;; | very good |        1 |         1 |         0 |      71 |     180 |       180 |   36 |       m |
;;; | very good |        0 |         1 |         1 |      63 |     165 |       120 |   31 |       f |
;;; |      good |        0 |         1 |         1 |      69 |     224 |       224 |   73 |       m |
;;; |      good |        1 |         1 |         0 |      66 |     215 |       140 |   23 |       f |
;;; | excellent |        0 |         1 |         0 |      73 |     200 |       185 |   35 |       m |
;;; |      poor |        0 |         1 |         0 |      65 |     216 |       150 |   57 |       f |
;;; |      good |        1 |         1 |         0 |      67 |     165 |       165 |   81 |       f |
;;; |      good |        1 |         1 |         1 |      69 |     170 |       165 |   83 |       m |
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; **
;;; You could also look at *all* of the data frame at once by typing its name into 
;;; the console, but that might be unwise here.  We know `cdc` has 20,000 rows, so 
;;; viewing the entire data set would mean flooding your screen.  It's better to 
;;; take small peeks at the data with `head`, `tail` or the subsetting techniques 
;;; that you'll learn in a moment.
;; **

;; **
;;; ## Summaries and tables
;; **

;; **
;;; The BRFSS questionnaire is a massive trove of information.  A good first step in
;;; any analysis is to distill all of that information into a few summary statistics
;;; and graphics.  As a simple example, the function `quantile` returns a numerical 
;;; summary: minimum, first quartile, median, second quartile, and maximum. 
;;; For `weight` this is
;; **

;; @@
(s/quantile (i/sel cdc :cols :weight))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-unkown'>(68.0 140.0 165.0 190.0 500.0)</span>","value":"(68.0 140.0 165.0 190.0 500.0)"}
;; <=

;; **
;;; here we are using `sel` function to get just a single column from the dataset.
;; **

;; **
;;; Incanter also has built-in functions to compute summary statistics one by one.  For 
;;; instance, to calculate the mean, median, and variance of `weight`, type
;; **

;; @@
(s/mean (i/sel cdc :cols :weight))
(s/median (i/sel cdc :cols :weight))
(s/variance (i/sel cdc :cols :weight))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-double'>1606.4841535051935</span>","value":"1606.4841535051935"}
;; <=

;; **
;;; While it makes sense to describe a quantitative variable like `weight` in terms
;;; of these statistics, what about categorical data?  We would instead consider the
;;; sample frequency or relative frequency distribution.  The function `table` does
;;; this for you by counting the number of times each kind of response was given.
;;; For example, to see the number of people who have smoked 100 cigarettes in their
;;; lifetime, type
;; **

;; @@
(print (i/$rollup :count :total :smoke100 cdc))
;; @@
;; ->
;;; 
;;; | :smoke100 | :total |
;;; |-----------+--------|
;;; |         0 |  10559 |
;;; |         1 |   9441 |
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; **
;;; TODO: a bit of explanation about `$rollup` function
;; **

;; **
;;; or instead look at the relative frequency distribution by typing
;; **

;; @@
(print (i/$rollup (fn [x] (/ (count x) 20000)) :freq :smoke100 cdc))
;; @@
;; ->
;;; 
;;; | :smoke100 |       :freq |
;;; |-----------+-------------|
;;; |         0 | 10559/20000 |
;;; |         1 |  9441/20000 |
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; **
;;; the numbers in the results are presented as rational numbers. To convert them to IEEE floating-point numbers type
;; **

;; @@
(float (/  10559 20000))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-unkown'>0.52795</span>","value":"0.52795"}
;; <=

;; **
;;; Next, we make a bar plot of the entries in the table by putting the table inside the `bar-chart` command.
;; **

;; @@
(def smoke100-chart
  (i/with-data
    (i/$rollup :count :total :smoke100 cdc)
    (c/bar-chart :smoke100 :total)))

(chart-view smoke100-chart)
;; @@
;; =>
;;; {"type":"html","content":"<img src=\"data:image/PNG;base64,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\"/>","value":"#incanter_gorilla.render.ChartView{:content #object[org.jfree.chart.JFreeChart 0x134bdb85 \"org.jfree.chart.JFreeChart@134bdb85\"], :opts nil}"}
;; <=

;; **
;;; Notice what we've done here! We've computed the dataset of `smoke100` distribution and then
;;; immediately applied the graphical function, `bar-chart`. You could also break this into two steps by 
;;; typing the following:
;; **

;; @@
(def smoke (i/$rollup :count :total :smoke100 cdc))

(def smoke100-chart
  (i/with-data smoke
    (c/bar-chart :smoke100 :total)))

(chart-view smoke100-chart)
;; @@
;; =>
;;; {"type":"html","content":"<img src=\"data:image/PNG;base64,iVBORw0KGgoAAAANSUhEUgAAAfQAAAE1CAYAAAARYhKbAAAghklEQVR42u2d7XdSV9qH55+c/2Q+5GNCIyaapIgWmEls6ggGHVuFKJ2oTbEaoXRK1YGpL7C0tmMCY5EXFUJS9pN7r+ecBQgpnHOSBrl+a13LbO4TkrggV+5z9tn7T4oQQgghI58/8V9ACCGEIHRCCCGEIHRCCCGEIHRCCCGEIHRCCCEEoRNCCCEEoffL7u6upVr3cbVazdEaIYQQgtAHzP/+9z81NTWldnZ2hqq15/vvv1fHjx9XH3/8sQoEAurNmze2a4QQQghCHzDXr19XLpdLTU5Ovift/Wrtqdfr6tixY6pYLOpxNBrVn2unRgghhCD0ISNdsUi716n1/WpGMpmM8vv95jifz+uO205tkPz222/6DAIAAIBTjLXQk8mkCgaD5lg6buns7dTaI1/foDutVgsAAMAxxlro8XhchcNhc1wqlfTnNJtNyzU6dAAAoEP/Azr0UCjUtwu3UkPoAACA0A9Z6NlstuNaeC6XM6+FW60hdAAAQOiHIPREIqHW1tb0x41GQ89WLxQKehyJRMzZ6lZrCB0AABD6EJFbxebm5rS05+fn1fr6+kA1r9erUqmUOZb7yd1ut/J4PMrn86lqtWq7htABAAChH2DK5bJeDGZ7e7vjcblXvVKp9PwcqzWEDgAACP2Akk6nVSwW+8O/D4QOAAAI3eY19+7uHKEDAABCJwgdAAAQOkJ3jnIioSrr6wBD8Wsuxy9CAIROjpLQd/7yF6X+/GeAoaiurfGLEAChE4QOCB0AEDpCR+iA0AEAoSN0hA4IHQAQOkIHQOgACJ0gdEDoAIDQETpCB4QOAAi9M/22R5XHa7XawM/R71irNYQOCB0AEPqAkR9gampKb5LSHtkJTTZhkT3KA4GAXvK1X/Y71moNoQNCBwCEPmBk/3GXy6W3SG0Xer1e13uVF4tFPZatVPvtVb7fsVZrCB0QOgAg9CEjXbEIvf20eyaTUX6/3xzn83ndRffKfsdarSF0QOgAgNAdEHoymVTBYNAcSxctnXyv7Hes1RpCB4QOAAjdAaHH43EVDofNcalU0sc0m833Pn+/Y63W2iOPGXSn1Wo5ipqYQFAwNK2NDcdfiwDwx/BBduihUGjgDr3fsVZrdOhAhw4AdOgOCD2bzXZc387lcn2vb+93rNUaQgeEDgAI3QGhNxoNPQO9UCjocSQS6ZiBnkgk1NreL7HfO9ZqDaEDQgcAhD5E5Faxubk5LfT5+Xm1vr7ecY+42+1WHo9H+Xw+Va1WzZrX61WpVGqgY63WEDogdABA6A5F7k2vVCodj5XLZb0YzPb29u8ea7eG0AGhAwBCP6Ck02kVi8VYyx0AoQMg9FEWulxz7+7OETogdH4RAiB0gtABoQMAQkfoCB0QOgAgdISO0AGhAwBCR+gACB0AoROEDggd9ignk+rd4iLAUFRXVxE6QkfogNCPEm8uXuS1BEOz/dFHCB2hI3RA6AgdEDpCR+gACB2hA0JH6AgdEDogdEDoh5pms6lqtdpAx8pubf2OtVpD6IDQEToAQreZW7duqenpaXXq1Cm1uLiol3ztF9k1TTZskf3MA4FAx7FWawgdEDpCB0DoNvP06VM1MzNjds6XL19Wq6urPY+t1+t6X/NisajHsiWrsa+51RpCB4SO0AEQugO5ceOGCofD5vjZs2e6i+6VTCaj/H6/Oc7n8+axVmsIHRA6QgdA6A4kHo+rlZUVc/zq1Ss1NTWlWq3We8cmk0kVDAbNsXTcLpfLVg2hA0JH6AAI3YFsbm7q6+f37t1Tjx8/VlevXtWy7SV0kX97N18qldTk5KSeUGe11h55zKA78v04iZqY4E0CQ9Pa2HD8tTh2yCU9XkswLLOzznvgQ5wUJ6fARbgX9/5yjsVifU+HS6cdCoX6duFWanToQIdOhw5Ah34AiUQi6sqVKz1r2Wy241p4Lpcz5W+1htABoSN0AITuUOTUg9wj/uDBA3XixAn18uVLs5ZIJNTa3i8xSaPR0LPVC4WCKX9jtrrVGkIHhI7QARC6Q5H7wmdnZ9XCwoJ68uRJR83r9apUKtVxP7nb7VYej0f5fD5VrVZt1xA6IHSEDoDQHYjcg95roZdyuawXg9ne3u54fGdnR1UqlZ7PZbWG0AGhI3QAhH5ASafTepIca7kDIHSEDgh9hCNde3d3jtABoSNlhA4InSB0QOgIndcSIHSEjtABoSN0QOgIHaEjdEDoCB0QOkJH6AAIHaEDQicIHRA6IHRA6AgdoQNCR+iA0BE6QkfogNAROiB0hI7QARA6QgeEThA6IHRA6IDQDyuy41qv9dx7RXZmk/XfnawhdEDoCB0AodvM3bt39X7lZ8+eVYuLi2pra6vvsbJrmmzYIvuZyy5t7X8EWK0hdEDoCB0AoduMbGM6MzOj9yw35H7p0qWex9brdb2vebFY1ONoNGrua261htABoSN0AITuQJ4/f96xRWo2m9Wdeq9kMhndyRvJ5/O647ZTQ+iA0BE6AEJ3aMLZuXPntHAfP36slpeX1ZMnT3oem0wmVTAYNMfScbtcLls1hA4IHaEDIHSH8s0336hQKKS8Xq86ffq0ev36dc/j4vG4CofD5rhUKqnJyUnVbDYt19ojjxn0mrTnJGpigjcJDE1rY8Px1+LYsbrKawmGZ3bWeQ98aEKX098iceMHvHbtmvL5fH07dBF/vy7cSo0OHejQ6dAB6NAdyPr6ulpZWTHHr1690h2yMUmuPXJ9vf1aeC6XM6+FW60hdEDoCB0AoTuQhw8fKrfbrd69e6fH3333XYdsE4mEWtv7JSYRycts9UKhoMeRSMScrW61htABoSN0AITu0IIyV65c0VJfWlrSE+R+/vlnsy7X1VOpVMf95HKsx+PRp+bltje7NYQOCB2hAyB0hyIderdky+Vyxy1tRnZ2dlSlUun5PFZrCB0QOkIHQOgHlHQ6rWKxGGu5AyB0hA4IfZSFLsuzdnfnCB0QOlJG6IDQCUIHhI7QeS0BQkfoCB0QOkIHhI7QETpCB4SO0AGhI3SEDoDQETogdILQAaEDQgeEjtAROiB0hA4IHaEjdIQOCB2hA0JH6AgdAKEjdEDoBKEDQgeEDgj9KGZ3d1fVajVHawgdEDpCB0DoNiI7qcn+593020RFdk2TDVtki9VAIKCXh7VbQ+iA0BE6AEK3Gdk+VTpng62trZ47rEnq9bre17xYLOpxNBo19zW3WkPogNAROgBCP4B8/vnn6ubNmz1rmUxG+f1+c5zP53XHbaeG0AGhI3QAhO5w5Ad0u93q7du3PevJZFIFg0FzLB23y+WyVUPogNAROgBCdziXL19Wa3u/sPolHo+rcDhsjkulkr7e3mw2Ldfa034Nv9elASdRExO8SWBoWhsbjr8Wx47VVV5LMDyzs8574EMVuvy1Ite595uFLp12KBTq24VbqdGhAx06HToAHbrD3fmXX3657zHZbLbjWngulzOvhVutIXRA6AgdAKE72J1PT0+rarX6Xi2RSJin4RuNhu7iC4WCHkciEXO2utUaQgeEjtABELqD3fm1a9d61rxer75Xvf1+cpk45/F4lM/n6/gjwGoNoQNCR+gACP0AUy6Xe96TvrOz03fhGas1hA4IHaEDIPQDSjqdVrFYjLXcARA6QgeEPspCl+VZe60Yh9ABoQNCB4ROEDogdIQOgNAROkIHhI7QAaEjdISO0AGhI3RA6AgdoQNCR8oIHRA6QeiA0BE6QgeEjtAROiB0hA4IfeSE/vLlS/Xo0aOBQOgIHRA6QgeEfkSF/sUXX3RsJ7ofCB2hA0JH6IDQETpCB0DoCB0Q+kEJXVZoe/v27UA4Fdnw/fXr12p3d3ff46Teb990qzWEDggdoQOMxaS4zc1NdffuXXXr1q33sBsRbTQa1buhzc/Pq3/96199j5Vd02TDFtnPPBAI6OVh7dYQOiB0hA4wFkJ//vy5crlcB3bKXU7vX7hwwfwBpVPvlXq9rvc1LxaLeix/BBj7mlutIXRA6AgdYGyEfnHvDSDinp6e1v+ePHlS71MuHy8sLNh6btnOVP5YMGS7XzKZjPL7/eY4n8/rjttODaEDQkfoAGMjdJ/Pp6VbrVbVR3s/1J07d8zOWmRvJ9lsVp9m/+c//6mWl5fV6upq39PhyWRSBYNBcyx/BMj3ZaeG0AGhI3SAsRG6x+NRZ86c0R9LZyunxyUiYenSRfRWk0gkdJd///599ezZMy3ec+fO9Tw2Ho+rcDhsjkulkv76zWbTcq09+11GkMsATqImJniTwNC0NjYcfy2OHXtNA68lGJrZWec98EcIfXFxUZ04cUJ/fPnyZS2806dPq6mpKY1doYdCofdk22g0enbo7cd2d+FWanToQIdOhw4wNh361atXtWR//vln3UWLxI1Otv1UtpX8+OOP+pR+t9B73Q4np+fbr4XncjnzWrjVGkIHhI7QAcZG6HJqQKTWPqksFoupdDr93mnrYfPu3Tt9O5n8sSC5ffu2+uSTTzo6+LW9X2IS6dpltnqhUNDjSCRizla3WkPogNAROsDYCH19fV199tln7z3+1Vdf6cfbZW8lDx8+VLOzs/pavXTOso68EZlNn0qlOu4nl/vV5Vjp7NtP91utIXRA6AgdYGxuW5PT7N2RLl1Oj8utZ3azs7OjV4lrnyhQLpd19y6r1nUf2+9rWq0hdEDoCB3ggxW6dLX37t3THa2IWz42kJnjMlFORN9rApsTkVP68kcDa7kDIHSEDgjd5u1qv7cxy9mzZw9MpHI/end3jtABoSNlhA4IfcjIzHA55W0s+yofG8hqcVeuXFG//PILu60hdEDoCB0Q+ihcQ5cZ4XLf+bgGoQNCR+iA0D+Y3daMSWVbW1vqxYsXjm6bitABEDpCB4R+SEL/9ttv9W1f7dfPZS132ckMoSN0QOgIHRD6CAhdVnPrNylOpI7QETogdIQOCH0EhH7+/Hktb1mx7aefftILv/zwww96MRh5/CjMREfogNABoQNCH+D2NVmxrTs3b97UQt/c3EToCB0QOkIHhH7UhS67rU1PT3csySqLySwtLdnePhWhAyB0hA4I/ZCELmu2G9fMZa11uYVNBC9jY590hI7QAaEjdEDoR1zocrua0Y23I0u/srAMQgeEjtABoY/QbWuyaUomk1E3btxQ165dU8lkUm99+kdkd3dX1Wo1R2sIHRA6Qgf44IV+0NunSqff3vnLJLx+kQ1jZOlZOfUfCAT0eu92awgdEDpCBxgLoR/09qkidLkdTjpood8fCLKIzbFjx1SxWNTjaDSql6W1U0PogNAROsAHL/TD2j510GvxcspfNowxks/ndcdtp4bQAaEjdIAPXuiHtX2qCP3ChQv62nw2m+17nFy3DwaD5lg6btkJzk6tPe0/V685BE6iJiZ4k8DQtDY2HH8tjh2rq7yWYHhmZ533wGEK/bC2T719+7ZeK/7rr7/Wq8/JGYBekbMC4XDYHJdKJf19NZtNyzU6dKBDp0MHGJtr6Ie5faqc5m8/Pd7doYdCob5duJUaQgeEjtABxuq2tcOKbARz6tSpnjU5Hd8u+1wuZ14Lt1pD6IDQETrAWN22try8rGm/Za37MSv59ddfzdP2Is6VlRUViUTMeiKR0JvCGMvNymz1QqGgx3KcMVvdag2hA0JH6ABjdduaMVnMuJAv16T7TSAbJrI+vFw3n5ub05PjPv300457xGVTmFQq1XFKXvZllwl7Mvu+fR15qzWEDggdoQOMhdBlktqlS5c0RjY2Nt57zM4qdOVy+b3FXuQxmYDXvT2rLEXb7953qzWEDggdoQNwDf2Akk6n9eI1rOUOgNAROiD0EY507N3dOUIHhI6UETogdILQAaEjdF5LgNAROkIHhI7QAaEjdISO0AGhI3RA6AgdoQMgdIQOCJ0gdEDogNABoSN0hA4IHaEDQkfoCB2hA0JH6IDQETpCB0DoCB0QOkHogNABoQNCP4rZ3d1VtVrN0RpCB4SO0AEQukN5/PixmpqaMrdT7RXZNU02bJH9zAOBQMeGLlZrCB0QOkIHQOgOZWtrS505c0ZvcdpP6PV6Xe9rXiwW9TgajZr7mlutIXRA6AgdAKE7FDkNLjKXvdFPnjzZV+iZTEb5/X5znM/ndcdtp4bQAaEjdACE7kBkj/LFxUWVzWb1eD+hJ5NJFQwGzbF03C6Xy1YNoQNCR+gACN2ByOnvL7/8Ur17905z4sQJlcvltES7E4/HVTgcNselUklNTk6qZrNpudYeecygO61Wy1HUxARvEhia1saG46/FsWN1ldcSDM/srPMe+NCEvrKyoubm5kxkUtzs3n/cixcvenbooVCobxdupUaHDnTodOgAdOgHkP1Ouctp+fZr4dLJG9fCrdYQOiB0hA6A0A9B6IlEQq3t/RKTNBoNPVu9UCjocSQSMWerW60hdEDoCB0AoR+C0L1er0qlUh33k8utbR6PR/l8PlWtVm3XEDogdIQOgNAPMOVyWS8Gs729/d7M+Eql0nfWvJUaQgeEjtABEPoBJZ1Oq1gsxlruAAgdoQNCH2Why/Ks3d05QgeEjpQROiB0gtABoSN0XkuA0BE6QgeEjtABoSN0hI7QAaEjdEDoCB2hAyB0hA4InSB0QOiA0AGhI3SEDggdoQNCR+gIHaEDQkfogNAROkIHQOgIHRA6QeiA0AGhA0I/rNRqNb12+yCbvu/u7urjnawhdEDoCB0AodvI69ev1cLCgpqbm9M7rZ06dUq9evWq7/Gya5ps2CL7mQcCAb08rN0aQgeEjtABELrNyDamP/30kzkOh8N6v/Jeqdfrel/zYrGox9Fo1NzX3GoNoQNCR+gACP0AcnHvzRaPx3vWMpmM8vv95jifz+uO204NoQNCR+gACN3B5HI5dfnyZXXhwgX19u3bnsckk0kVDAbNsXTcLpfLVg2hA0JH6AAI3cHINW6R+dLSUt8fVDp3OSVvpFQqqcnJSdVsNi3X2iOPGXRHJus5iZqY4E0CQ9Pa2HD8tTh2rK7yWoLhmZ113gMf+il3kW8oFOrbobfXurtwKzU6dKBDp0MHoEM/gDx48ED5fL6etWw223EtXE7TG9fCrdYQOiB0hA6A0B2IzHDf2trSH+/s7Kjz58+rVTkl9v9JJBJqbe+XmKTRaOjZ6oVCQY9lNrwxW91qDaEDQkfoAAjdgTx69EjfHy73oM/MzKjl5eWOSXFer1elUqmOa+1ut1t5PB7dycttb3ZrCB0QOkIHQOgOLa0qC8x0L/YiK8eJ7Le3tzsel06+Uqn0fC6rNYQOCB2hAyD0A0o6nVaxWIy13AEQOkIHhD7KQpeOvbs7R+iA0JEyQgeEThA6IHSEzmsJEDpCR+iA0BE6IHSEjtAROiB0hA4IHaEjdACEjtABoROEDggdEDogdISO0AGhI3RA6AgdoSN0QOgIHRA6QkfoAAgdoQNCJwgdEDogdEDoh5VarTbwqnC7u7v6eCdrCB0QOkIHQOg2IhuwLCwsqLm5OTU/P69WVlZUs9nse7zsmiYbtsh+5oFAoGNDF6s1hA4IHaEDIHQHOnPZE93ooJeWltT9+/d7Hluv1/W+5sViUY+j0ai5r7nVGkIHhI7QARD6AURkG4lEetYymYzy+/3mOJ/P647bTg2hA0JH6AAI/QDi9XrVt99+27OWTCZVMBg0x9Jxu1wuWzWEDggdoQMgdIdz584d5fF4+k6Oi8fjKhwOm+NSqaQmJyf1NXertfbIYwbdabVajqImJniTwNC0NjYcfy2OHaurvJZgeGZnnffAhyr058+fq5mZGbW5udn3GOm0Q6FQ3y7cSo0OHejQ6dAB6NAdytbWlp7l/uzZs32Py2azHdfCc7mceS3cag2hA0JH6AAI3YG8evVK37Ymku2VRCKh1vZ+iUkajYaerV4oFPRYJs8Zs9Wt1hA6IHSEDoDQHciDBw86rl0biESNSXKpVKrjfnK3262vtft8PlWtVm3XEDogdIQOgNAPeNEZWQyme5Lczs6OqlQqPT/Hag2hA0JH6AAI/YCSTqdVLBZjLXcAhI7QAaGPstBledZB13dH6IDQAaEDQicIHRA6QgdA6AgdoQNCR+iA0BE6QgdA6AgdEDpCR+iA0AGhA0InCB0QOkIHQOgIHaEDQkfogNAROkIHQOgIHRA6QkfogNABoQNCJwgdEDpCB0DoB5Hd3d2Bj6vVao7WEDogdIQOgNAdiPxwU1NTegOV/SK7psmGLbKfeSAQ0MvD2q0hdEDoCB0AoTsQ2Zvc5XLpbVP3E3q9Xtf7mheLRT2ORqPmvuZWawgdEDpCB0DoDkY6ZhH6fqfdM5mM8vv95jifz+uO204NoQNCR+gACP2QhZ5MJlUwGDTH0nFLZ2+nhtABoSN0AIR+yEKPx+MqHA6b41KppD+n2WxarrVHHjPoTqvVchQ1McGbBIamtbHh+Gtx7Fhd5bUEwzM767wHxr1DD4VCfbtwKzU6dKBDp0MHoEM/ZKFns9mOa+G5XM68Fm61htABoSN0AIR+CEJPJBJqbe+XmKTRaOjZ6oVCQY8jkYg5W91qDaEDQkfoAAjdochtZHNzc1ro8/Pzan193ax5vV6VSqU67id3u93K4/Eon8+nqtWq7RpCB4SO0AEQ+gGmXC7rxWC2t7c7Hpd71SuVSs/PsVpD6IDQEToAQj+gpNNpFYvFWMsdAKEjdEDooyx0ua7e3Z0jdEDoSBmhA0InCB0QOkLntQQIHaEjdEDoCB0QOkJH6AgdEDpCB4SO0BE6AEJH6IDQCUIHhA4IHRA6QkfogNAROiB0hI7QETogdIQOCB2hI3QAhI7QAaEThA4IHRA6IPQPKbKTW61WQ+iA0BE6AEIf1chua7LRi+yDHggE9LKyCB0QOkIHQOgjlHq9rvdDLxaLeizbtbIfOiB0hA6A0EcsmUxG+f1+c5zP53WnjtABoSN0AIQ+QkkmkyoYDJpj6dRdLhdCB4SO0AEQ+iglHo+rcDhsjkulkpqcnFTNZrPjOHnMoDutVstZ/vEPpXw+gKFoPX3q/Gtx3Lh/n9cSDP/eu37d8dciQrfYoYdCIcsdOiGEEPJHB6HvJZvNdlxDz+VyQ11DJ4QQQhD6EUij0dCz3AuFgh5HIpGhZrkTQgghCP2IRO5Dd7vdyuPxKJ/Pp6rVKv8pRzS95jAQQnjvIXRiZmdnR1UqFf4j+KVCCOG9h9AJ4ZcKIbz3CEInhBBCCEInhBBCEDohhBBCEDohhBBCEDohhBBCEDohhBCC0AkZkezu7qparcZ/BCF/0PuPIHRCbEdW8zt+/LheZz8QCKg3b97wn0LIIUW285yamtILcBGETojl1Ot1vd6+7IQniUajrLdPyCFF3muyA6UsLIPQETohtpLJZDp2xMvn8+yIR8ghRs6IidA57Y7QCbEV2bM+GAyaY/asJwShE4RORjDxeFyFw2FzXCqV9C+XZrPJfw4hCJ0gdDJKHXooFKJDJwShE4RORjnZbLbjGnoul+MaOiEInSB0MmppNBp6lnuhUNDjSCTCLHdCEDpB6GQUI/ehu91u5fF4lM/nU9Vqlf8UQg4hcpvo3NycFvr8/LxaX1/nPwWhE2Ivcg9spVLhP4IQQhA6IYQQgtAJIYQQgtAJIYQQgtAJIYQQgtAJIYQQhE4IIYQQhE4IIYQQhE4IIYQQhE4IIYQgdEIIIYQgdEIIIYQgdELIyGZ7e1t98cUXmpcvX9p6Ltlx77///W/f55FdwKSeyWTU5uam+u233ywdQwhCJ4SQrrx9+1bv0CU8evTI8vO0Wi21urqqn2d6evq9+qtXr5TX6zW/luD3+zs28hnkGEIQOiGEHJDQY7GY3jbXeJ5eQv/rX/9q1q5cuaJcLpceB4PBoY4hBKETQo5cvv76a/Xpp59qIRp5/vy5On/+vN7b+sSJE2ppaUmffq7X6/pYQcaXLl1SJ0+eVKFQSNeePHmij5XHvvrqK/P5pCad86lTp9SxY8dUIBBQ6XR6X6HfvHlTf52///3vqlarqWazqW7cuKHOnDmjn0P+TSaTuiuXyN7bZ8+eVQsLC/p55Jj2yCl042s8fPhQP5ZIJMzHyuXyQMcQgtAJIUcyImWR1fLysik+oyv95JNP1Llz57QcP//88w7xdtN9mlqQPwzk+rPP59PjmZkZ9be//c2sf/PNNz2F/uDBA3P8/fff62NE1sbXuXjxovroo4/0OJVKdfw88odEL6H/+9//Np+zWCzqx549e2Y+Jh8PcgwhCJ0QciQjopbTy9KRS+LxuJaXCFM6Y4lMNHv69GmHeEWcUv/ss8/0WE53y0Q0wThGutt2ORuSlG7akK503u3Pe/fuXf24fHzt2jV9fDab1ePjx4/r4yVra2v6MfljYRChSzdvfA3jenj79yoyH+QYQhA6IWQkIuI2BCadunTU9+7d0512r1PjhkDl9LyR2dlZ/Zj8cWCI9+OPPzbrL168MJ9HZpG3P6/Recupc2N2uZx+N2oej0cjlwJ6XSvvJ/TvvvvO/BqvX7/Wj/3yyy/mY//5z38GOoYQhE4IGYnINWnpVOX0ePvp86tXr/YUulyD7xa6XEM3hH79+nX98enTp816+7VqkXv78xqf2/41jOcQiUvX3s0gQv/xxx/N5zVuaWv/40W+j0GOIQShE0KOZO7cuaMntUkXbMhWOmMRu0hNrqEbwrYidOM0tnTScupe8sMPP5jP8+7du47nlS5YJtYZzykT6uQ6uSHparXa8f3LdfpBhC4dd/vlAolxi5t0/vJ9DHIMIQidEHIk0z0pTgQmp7RlRvnt27f1x8ZtW1aELhI0To+LqOV5jVPyspCMpPt5t7a21NTUlHlmQP4QkFP2xteR71Fm5cv1c5k5L7l165ZaXFxU8/Pz+jj5fBkLxnV3+Rl6TeIzvo9BjyEEoRNCjrzQpRuWyWftp9svXLig3rx5Y0noErkOLbeZtT+nCFK6715ClxjX3oVcLqcXfJEzCe3PIdf4DdEaP0cvjDMD8vVE2MYfC/KvfJ6sVGdkkGMIQeiEkJGInHKXe64LhYIpQycip8tlEpwdOcqyrCJ3mTG/s7Nj6TmkY/+9zx/kGEIQOiGEEEIQOiGEEEIQOiGEEILQCSGEEILQCSGEEILQCSGEEILQCSGEkDHP/wE4M1y0K8PdRwAAAABJRU5ErkJggg==\"/>","value":"#incanter_gorilla.render.ChartView{:content #object[org.jfree.chart.JFreeChart 0x6f13c773 \"org.jfree.chart.JFreeChart@6f13c773\"], :opts nil}"}
;; <=

;; **
;;; *2.  Create a numerical summary for `height` and `age`, and compute the interquartile range for each. Compute the relative frequency distribution for `gender` and `exerany`. How many males are in the sample? What proportion of the sample reports being in excellent health?*
;; **

;; **
;;; The `rollup` and `bar-chart` commands can be used to build multivariable bar charts. For example, to examine which participants have smoked across each gender, we could use the following.
;; **

;; @@
(def gender-smoke (i/$rollup :count :total [:smoke100 :gender] cdc))
(print gender-smoke)
;; @@
;; ->
;;; 
;;; | :smoke100 | :gender | :total |
;;; |-----------+---------+--------|
;;; |         0 |       m |   4547 |
;;; |         1 |       f |   4419 |
;;; |         0 |       f |   6012 |
;;; |         1 |       m |   5022 |
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; @@
(def gender-smoke-chart
  (i/with-data gender-smoke
    (c/bar-chart :gender :total
                 :group-by :smoke100
                 :legend true)))

(chart-view gender-smoke-chart)
;; @@
;; =>
;;; {"type":"html","content":"<img src=\"data:image/PNG;base64,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\"/>","value":"#incanter_gorilla.render.ChartView{:content #object[org.jfree.chart.JFreeChart 0x1b11c19e \"org.jfree.chart.JFreeChart@1b11c19e\"], :opts nil}"}
;; <=

;; **
;;; Also we can customize this chart using JFreeChart API
;; **

;; @@
(import org.jfree.chart.renderer.category.LayeredBarRenderer
        org.jfree.util.SortOrder)
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-class'>org.jfree.util.SortOrder</span>","value":"org.jfree.util.SortOrder"}
;; <=

;; @@
(doto (.getPlot gender-smoke-chart)
     (.setRenderer (doto (LayeredBarRenderer.)
                    (.setDrawBarOutline false)))
     (.setRowRenderingOrder SortOrder/DESCENDING))
(chart-view gender-smoke-chart)
;; @@
;; =>
;;; {"type":"html","content":"<img src=\"data:image/PNG;base64,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\"/>","value":"#incanter_gorilla.render.ChartView{:content #object[org.jfree.chart.JFreeChart 0xd6d85d3 \"org.jfree.chart.JFreeChart@d6d85d3\"], :opts nil}"}
;; <=

;; **
;;; *3.  What does grouped bar chart reveal about smoking habits and gender?*
;; **

;; **
;;; ## Interlude: How Incanter thinks about data
;; **

;; **
;;; Incanter stores data in datasets, which you might think of as a 
;;; type of spreadsheet. Each row is a different observation (a different respondent)
;;; and each column is a different variable (the first is `genhlth`, the second 
;;; `exerany` and so on). To see the size of the dataset we can type
;; **

;; @@
(i/dim cdc)
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-unkown'>20000</span>","value":"20000"},{"type":"html","content":"<span class='clj-unkown'>9</span>","value":"9"}],"value":"[20000 9]"}
;; <=

;; **
;;; which will return the number of rows and columns. Now, if we want to access a subset of the full data frame. For example, to see the sixth variable of the 567<sup>th</sup> row, use the format
;; **

;; @@
(i/sel cdc :rows 567 :cols 5)
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-long'>172</span>","value":"172"}
;; <=

;; **
;;; which means we want the element of our data set that is in the 567<sup>th</sup>
;;; row (meaning the 568<sup>th</sup> (zero-based numbering) person or observation) and the 5<sup>th</sup> 
;;; column (in this case, weight). We know that `weight` is the 5<sup>th</sup> variable (zero-based numbering)
;;; because it is the 5<sup>th</sup> entry (starting from 0) in the list of variable names
;; **

;; **
;;; To see the weights for the first 10 respondents we can type
;; **

;; @@
(i/sel cdc :rows (range 10) :cols 5)
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-lazy-seq'>(</span>","close":"<span class='clj-lazy-seq'>)</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-long'>175</span>","value":"175"},{"type":"html","content":"<span class='clj-long'>125</span>","value":"125"},{"type":"html","content":"<span class='clj-long'>105</span>","value":"105"},{"type":"html","content":"<span class='clj-long'>132</span>","value":"132"},{"type":"html","content":"<span class='clj-long'>150</span>","value":"150"},{"type":"html","content":"<span class='clj-long'>114</span>","value":"114"},{"type":"html","content":"<span class='clj-long'>194</span>","value":"194"},{"type":"html","content":"<span class='clj-long'>170</span>","value":"170"},{"type":"html","content":"<span class='clj-long'>150</span>","value":"150"},{"type":"html","content":"<span class='clj-long'>180</span>","value":"180"}],"value":"(175 125 105 132 150 114 194 170 150 180)"}
;; <=

;; **
;;; In this expression, we have asked just for rows in the range from 0 (inclusive) to 10 (exclusive).
;; **

;; @@
(range 10)
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-unkown'>(0 1 2 3 4 5 6 7 8 9)</span>","value":"(0 1 2 3 4 5 6 7 8 9)"}
;; <=

;; **
;;; To get all the columns we could pass `:all` keyword for `:cols` option or just omit this option. Similarly for the `:rows` we could access all the observations, not just the 567<sup>th</sup>, or rows 0 
;;; through 9. Try the following to see the weights for all 20,000 respondents fly 
;;; by on your screen
;; **

;; @@
;; (print (i/sel cdc :cols 5))
;; @@

;; **
;;; Recall that column 5 represents respondents’ weight, so the command above reported all of the weights in the dataset. An alternative method to access the weight data is by referring to the column name. Previously, we typed names(cdc) to see all the columns contained in the cdc dataset. We can use any of the column names to select items in our dataset.
;; **

;; @@
;; (print (i/sel cdc :cols :weight))
;; @@

;; **
;;; Since the result is a `clojure.lang.LazySeq`, we can subset it with just a single index. We see the weight for the 568<sup>th</sup> respondent by typing
;; **

;; @@
(nth (i/sel cdc :cols :weight) 567)
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-long'>172</span>","value":"172"}
;; <=

;; **
;;; Similarly, for just the first 10 respondents
;; **

;; @@
(take 10 (i/sel cdc :cols :weight))
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-lazy-seq'>(</span>","close":"<span class='clj-lazy-seq'>)</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-long'>175</span>","value":"175"},{"type":"html","content":"<span class='clj-long'>125</span>","value":"125"},{"type":"html","content":"<span class='clj-long'>105</span>","value":"105"},{"type":"html","content":"<span class='clj-long'>132</span>","value":"132"},{"type":"html","content":"<span class='clj-long'>150</span>","value":"150"},{"type":"html","content":"<span class='clj-long'>114</span>","value":"114"},{"type":"html","content":"<span class='clj-long'>194</span>","value":"194"},{"type":"html","content":"<span class='clj-long'>170</span>","value":"170"},{"type":"html","content":"<span class='clj-long'>150</span>","value":"150"},{"type":"html","content":"<span class='clj-long'>180</span>","value":"180"}],"value":"(175 125 105 132 150 114 194 170 150 180)"}
;; <=

;; **
;;; ## A little more on subsetting
;; **

;; **
;;; It's often useful to extract all individuals (cases) in a dataset that have specific characteristics. We accomplish this with Incanter's query language using `$where` function. 
;; **

;; **
;;; Suppose we want to extract just the data for the men in the sample. We can use the Incanter function `$where` to do that for us. For example, the command
;; **

;; @@
(def mdata
  (i/$where {:gender "m"} cdc))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;openintro.intro-to-data/mdata</span>","value":"#'openintro.intro-to-data/mdata"}
;; <=

;; **
;;; will create a new dataset called `mdata` that contains only the men from the `cdc` data set. In addition to finding it in your workspace alongside its dimensions, you can take a peek at the first several rows as usual
;; **

;; @@
(print (i/head mdata))
;; @@
;; ->
;;; 
;;; |  :genhlth | :exerany | :hlthplan | :smoke100 | :height | :weight | :wtdesire | :age | :gender |
;;; |-----------+----------+-----------+-----------+---------+---------+-----------+------+---------|
;;; |      good |        0 |         1 |         0 |      70 |     175 |       175 |   77 |       m |
;;; | very good |        1 |         1 |         0 |      71 |     194 |       185 |   31 |       m |
;;; | very good |        0 |         1 |         0 |      67 |     170 |       160 |   45 |       m |
;;; |      good |        1 |         1 |         0 |      70 |     180 |       170 |   44 |       m |
;;; | excellent |        1 |         1 |         1 |      69 |     186 |       175 |   46 |       m |
;;; |      fair |        1 |         1 |         1 |      69 |     168 |       148 |   62 |       m |
;;; | excellent |        1 |         0 |         1 |      66 |     185 |       220 |   21 |       m |
;;; | excellent |        1 |         1 |         1 |      70 |     170 |       170 |   69 |       m |
;;; |      fair |        1 |         0 |         0 |      69 |     170 |       170 |   23 |       m |
;;; |      good |        1 |         1 |         1 |      73 |     185 |       175 |   79 |       m |
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; **
;;; This new data set contains all the same variables but just under half the rows.
;;; It is also possible to tell Incanter to keep only specific variables, which is a topic
;;; we'll discuss in a future lab. For now, the important thing is that we can carve
;;; up the data based on values of one or more variables.
;; **

;; **
;;; You can use conjunction of several conditions
;; **

;; @@
(def m_and_over30 
  (i/$where {:gender "m" :age {:gt 30}} cdc))

(print (i/head m_and_over30))
;; @@
;; ->
;;; 
;;; |  :genhlth | :exerany | :hlthplan | :smoke100 | :height | :weight | :wtdesire | :age | :gender |
;;; |-----------+----------+-----------+-----------+---------+---------+-----------+------+---------|
;;; |      good |        0 |         1 |         0 |      70 |     175 |       175 |   77 |       m |
;;; | very good |        1 |         1 |         0 |      71 |     194 |       185 |   31 |       m |
;;; | very good |        0 |         1 |         0 |      67 |     170 |       160 |   45 |       m |
;;; |      good |        1 |         1 |         0 |      70 |     180 |       170 |   44 |       m |
;;; | excellent |        1 |         1 |         1 |      69 |     186 |       175 |   46 |       m |
;;; |      fair |        1 |         1 |         1 |      69 |     168 |       148 |   62 |       m |
;;; | excellent |        1 |         1 |         1 |      70 |     170 |       170 |   69 |       m |
;;; |      good |        1 |         1 |         1 |      73 |     185 |       175 |   79 |       m |
;;; |      good |        0 |         0 |         1 |      67 |     156 |       150 |   47 |       m |
;;; |      fair |        0 |         1 |         1 |      71 |     185 |       185 |   76 |       m |
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; **
;;; This will give you the data for men over the age of 30.
;; **

;; **
;;; To use disjunction of several conditions you can do this
;; **

;; @@
(def m_or_under30
  (i/$where (i/$fn [gender age] (or (= gender "m") (< age 30))) cdc))

(print (i/head m_or_over30))
;; @@
;; ->
;;; 
;;; |  :genhlth | :exerany | :hlthplan | :smoke100 | :height | :weight | :wtdesire | :age | :gender |
;;; |-----------+----------+-----------+-----------+---------+---------+-----------+------+---------|
;;; |      good |        0 |         1 |         0 |      70 |     175 |       175 |   77 |       m |
;;; | very good |        1 |         1 |         0 |      71 |     194 |       185 |   31 |       m |
;;; | very good |        0 |         1 |         0 |      67 |     170 |       160 |   45 |       m |
;;; |      good |        0 |         1 |         1 |      65 |     150 |       130 |   27 |       f |
;;; |      good |        1 |         1 |         0 |      70 |     180 |       170 |   44 |       m |
;;; | excellent |        1 |         1 |         1 |      69 |     186 |       175 |   46 |       m |
;;; |      fair |        1 |         1 |         1 |      69 |     168 |       148 |   62 |       m |
;;; | excellent |        1 |         0 |         1 |      66 |     185 |       220 |   21 |       m |
;;; | excellent |        1 |         1 |         1 |      70 |     170 |       170 |   69 |       m |
;;; |      fair |        1 |         0 |         0 |      69 |     170 |       170 |   23 |       m |
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; **
;;; This will take people who are men or under the age of 30 (why that's an interesting 
;;; group is hard to say, but right now the mechanics of this are the important 
;;; thing).
;; **

;; **
;;; *4. Create a new object called `under23_and_smoke` that contains all observations 
;;;     of respondents under the age of 23 that have smoked 100 cigarettes in their 
;;;     lifetime. Write the command you used to create the new object as the answer
;;;     to this exercise.*
;; **

;; @@

;; @@
