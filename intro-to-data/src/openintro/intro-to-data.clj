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
;;; {"type":"html","content":"<img src=\"data:image/PNG;base64,iVBORw0KGgoAAAANSUhEUgAAAfQAAAE1CAYAAAARYhKbAAAghklEQVR42u2d7XdSV9qH55+c/2Q+5GNCIyaapIgWmEls6ggGHVuFKJ2oTbEaoXRK1YGpL7C0tmMCY5EXFUJS9pN7r+ecBQgpnHOSBrl+a13LbO4TkrggV+5z9tn7T4oQQgghI58/8V9ACCGEIHRCCCGEIHRCCCGEIHRCCCGEIHRCCCEEoRNCCCEEoffL7u6upVr3cbVazdEaIYQQgtAHzP/+9z81NTWldnZ2hqq15/vvv1fHjx9XH3/8sQoEAurNmze2a4QQQghCHzDXr19XLpdLTU5Ovift/Wrtqdfr6tixY6pYLOpxNBrVn2unRgghhCD0ISNdsUi716n1/WpGMpmM8vv95jifz+uO205tkPz222/6DAIAAIBTjLXQk8mkCgaD5lg6buns7dTaI1/foDutVgsAAMAxxlro8XhchcNhc1wqlfTnNJtNyzU6dAAAoEP/Azr0UCjUtwu3UkPoAACA0A9Z6NlstuNaeC6XM6+FW60hdAAAQOiHIPREIqHW1tb0x41GQ89WLxQKehyJRMzZ6lZrCB0AABD6EJFbxebm5rS05+fn1fr6+kA1r9erUqmUOZb7yd1ut/J4PMrn86lqtWq7htABAAChH2DK5bJeDGZ7e7vjcblXvVKp9PwcqzWEDgAACP2Akk6nVSwW+8O/D4QOAAAI3eY19+7uHKEDAABCJwgdAAAQOkJ3jnIioSrr6wBD8Wsuxy9CAIROjpLQd/7yF6X+/GeAoaiurfGLEAChE4QOCB0AEDpCR+iA0AEAoSN0hA4IHQAQOkIHQOgACJ0gdEDoAIDQETpCB4QOAAi9M/22R5XHa7XawM/R71irNYQOCB0AEPqAkR9gampKb5LSHtkJTTZhkT3KA4GAXvK1X/Y71moNoQNCBwCEPmBk/3GXy6W3SG0Xer1e13uVF4tFPZatVPvtVb7fsVZrCB0QOgAg9CEjXbEIvf20eyaTUX6/3xzn83ndRffKfsdarSF0QOgAgNAdEHoymVTBYNAcSxctnXyv7Hes1RpCB4QOAAjdAaHH43EVDofNcalU0sc0m833Pn+/Y63W2iOPGXSn1Wo5ipqYQFAwNK2NDcdfiwDwx/BBduihUGjgDr3fsVZrdOhAhw4AdOgOCD2bzXZc387lcn2vb+93rNUaQgeEDgAI3QGhNxoNPQO9UCjocSQS6ZiBnkgk1NreL7HfO9ZqDaEDQgcAhD5E5Faxubk5LfT5+Xm1vr7ecY+42+1WHo9H+Xw+Va1WzZrX61WpVGqgY63WEDogdABA6A5F7k2vVCodj5XLZb0YzPb29u8ea7eG0AGhAwBCP6Ck02kVi8VYyx0AoQMg9FEWulxz7+7OETogdH4RAiB0gtABoQMAQkfoCB0QOgAgdISO0AGhAwBCR+gACB0AoROEDggd9ignk+rd4iLAUFRXVxE6QkfogNCPEm8uXuS1BEOz/dFHCB2hI3RA6AgdEDpCR+gACB2hA0JH6AgdEDogdEDoh5pms6lqtdpAx8pubf2OtVpD6IDQEToAQreZW7duqenpaXXq1Cm1uLiol3ztF9k1TTZskf3MA4FAx7FWawgdEDpCB0DoNvP06VM1MzNjds6XL19Wq6urPY+t1+t6X/NisajHsiWrsa+51RpCB4SO0AEQugO5ceOGCofD5vjZs2e6i+6VTCaj/H6/Oc7n8+axVmsIHRA6QgdA6A4kHo+rlZUVc/zq1Ss1NTWlWq3We8cmk0kVDAbNsXTcLpfLVg2hA0JH6AAI3YFsbm7q6+f37t1Tjx8/VlevXtWy7SV0kX97N18qldTk5KSeUGe11h55zKA78v04iZqY4E0CQ9Pa2HD8tTh2yCU9XkswLLOzznvgQ5wUJ6fARbgX9/5yjsVifU+HS6cdCoX6duFWanToQIdOhw5Ah34AiUQi6sqVKz1r2Wy241p4Lpcz5W+1htABoSN0AITuUOTUg9wj/uDBA3XixAn18uVLs5ZIJNTa3i8xSaPR0LPVC4WCKX9jtrrVGkIHhI7QARC6Q5H7wmdnZ9XCwoJ68uRJR83r9apUKtVxP7nb7VYej0f5fD5VrVZt1xA6IHSEDoDQHYjcg95roZdyuawXg9ne3u54fGdnR1UqlZ7PZbWG0AGhI3QAhH5ASafTepIca7kDIHSEDgh9hCNde3d3jtABoSNlhA4InSB0QOgIndcSIHSEjtABoSN0QOgIHaEjdEDoCB0QOkJH6AAIHaEDQicIHRA6IHRA6AgdoQNCR+iA0BE6QkfogNAROiB0hI7QARA6QgeEThA6IHRA6IDQDyuy41qv9dx7RXZmk/XfnawhdEDoCB0AodvM3bt39X7lZ8+eVYuLi2pra6vvsbJrmmzYIvuZyy5t7X8EWK0hdEDoCB0AoduMbGM6MzOj9yw35H7p0qWex9brdb2vebFY1ONoNGrua261htABoSN0AITuQJ4/f96xRWo2m9Wdeq9kMhndyRvJ5/O647ZTQ+iA0BE6AEJ3aMLZuXPntHAfP36slpeX1ZMnT3oem0wmVTAYNMfScbtcLls1hA4IHaEDIHSH8s0336hQKKS8Xq86ffq0ev36dc/j4vG4CofD5rhUKqnJyUnVbDYt19ojjxn0mrTnJGpigjcJDE1rY8Px1+LYsbrKawmGZ3bWeQ98aEKX098iceMHvHbtmvL5fH07dBF/vy7cSo0OHejQ6dAB6NAdyPr6ulpZWTHHr1690h2yMUmuPXJ9vf1aeC6XM6+FW60hdEDoCB0AoTuQhw8fKrfbrd69e6fH3333XYdsE4mEWtv7JSYRycts9UKhoMeRSMScrW61htABoSN0AITu0IIyV65c0VJfWlrSE+R+/vlnsy7X1VOpVMf95HKsx+PRp+bltje7NYQOCB2hAyB0hyIderdky+Vyxy1tRnZ2dlSlUun5PFZrCB0QOkIHQOgHlHQ6rWKxGGu5AyB0hA4IfZSFLsuzdnfnCB0QOlJG6IDQCUIHhI7QeS0BQkfoCB0QOkIHhI7QETpCB4SO0AGhI3SEDoDQETogdILQAaEDQgeEjtAROiB0hA4IHaEjdIQOCB2hA0JH6AgdAKEjdEDoBKEDQgeEDgj9KGZ3d1fVajVHawgdEDpCB0DoNiI7qcn+593020RFdk2TDVtki9VAIKCXh7VbQ+iA0BE6AEK3Gdk+VTpng62trZ47rEnq9bre17xYLOpxNBo19zW3WkPogNAROgBCP4B8/vnn6ubNmz1rmUxG+f1+c5zP53XHbaeG0AGhI3QAhO5w5Ad0u93q7du3PevJZFIFg0FzLB23y+WyVUPogNAROgBCdziXL19Wa3u/sPolHo+rcDhsjkulkr7e3mw2Ldfa034Nv9elASdRExO8SWBoWhsbjr8Wx47VVV5LMDyzs8574EMVuvy1Ite595uFLp12KBTq24VbqdGhAx06HToAHbrD3fmXX3657zHZbLbjWngulzOvhVutIXRA6AgdAKE72J1PT0+rarX6Xi2RSJin4RuNhu7iC4WCHkciEXO2utUaQgeEjtABELqD3fm1a9d61rxer75Xvf1+cpk45/F4lM/n6/gjwGoNoQNCR+gACP0AUy6Xe96TvrOz03fhGas1hA4IHaEDIPQDSjqdVrFYjLXcARA6QgeEPspCl+VZe60Yh9ABoQNCB4ROEDogdIQOgNAROkIHhI7QAaEjdISO0AGhI3RA6AgdoQNCR8oIHRA6QeiA0BE6QgeEjtAROiB0hA4IfeSE/vLlS/Xo0aOBQOgIHRA6QgeEfkSF/sUXX3RsJ7ofCB2hA0JH6IDQETpCB0DoCB0Q+kEJXVZoe/v27UA4Fdnw/fXr12p3d3ff46Teb990qzWEDggdoQOMxaS4zc1NdffuXXXr1q33sBsRbTQa1buhzc/Pq3/96199j5Vd02TDFtnPPBAI6OVh7dYQOiB0hA4wFkJ//vy5crlcB3bKXU7vX7hwwfwBpVPvlXq9rvc1LxaLeix/BBj7mlutIXRA6AgdYGyEfnHvDSDinp6e1v+ePHlS71MuHy8sLNh6btnOVP5YMGS7XzKZjPL7/eY4n8/rjttODaEDQkfoAGMjdJ/Pp6VbrVbVR3s/1J07d8zOWmRvJ9lsVp9m/+c//6mWl5fV6upq39PhyWRSBYNBcyx/BMj3ZaeG0AGhI3SAsRG6x+NRZ86c0R9LZyunxyUiYenSRfRWk0gkdJd///599ezZMy3ec+fO9Tw2Ho+rcDhsjkulkv76zWbTcq09+11GkMsATqImJniTwNC0NjYcfy2OHXtNA68lGJrZWec98EcIfXFxUZ04cUJ/fPnyZS2806dPq6mpKY1doYdCofdk22g0enbo7cd2d+FWanToQIdOhw4wNh361atXtWR//vln3UWLxI1Otv1UtpX8+OOP+pR+t9B73Q4np+fbr4XncjnzWrjVGkIHhI7QAcZG6HJqQKTWPqksFoupdDr93mnrYfPu3Tt9O5n8sSC5ffu2+uSTTzo6+LW9X2IS6dpltnqhUNDjSCRizla3WkPogNAROsDYCH19fV199tln7z3+1Vdf6cfbZW8lDx8+VLOzs/pavXTOso68EZlNn0qlOu4nl/vV5Vjp7NtP91utIXRA6AgdYGxuW5PT7N2RLl1Oj8utZ3azs7OjV4lrnyhQLpd19y6r1nUf2+9rWq0hdEDoCB3ggxW6dLX37t3THa2IWz42kJnjMlFORN9rApsTkVP68kcDa7kDIHSEDgjd5u1qv7cxy9mzZw9MpHI/end3jtABoSNlhA4IfcjIzHA55W0s+yofG8hqcVeuXFG//PILu60hdEDoCB0Q+ihcQ5cZ4XLf+bgGoQNCR+iA0D+Y3daMSWVbW1vqxYsXjm6bitABEDpCB4R+SEL/9ttv9W1f7dfPZS132ckMoSN0QOgIHRD6CAhdVnPrNylOpI7QETogdIQOCH0EhH7+/Hktb1mx7aefftILv/zwww96MRh5/CjMREfogNABoQNCH+D2NVmxrTs3b97UQt/c3EToCB0QOkIHhH7UhS67rU1PT3csySqLySwtLdnePhWhAyB0hA4I/ZCELmu2G9fMZa11uYVNBC9jY590hI7QAaEjdEDoR1zocrua0Y23I0u/srAMQgeEjtABoY/QbWuyaUomk1E3btxQ165dU8lkUm99+kdkd3dX1Wo1R2sIHRA6Qgf44IV+0NunSqff3vnLJLx+kQ1jZOlZOfUfCAT0eu92awgdEDpCBxgLoR/09qkidLkdTjpood8fCLKIzbFjx1SxWNTjaDSql6W1U0PogNAROsAHL/TD2j510GvxcspfNowxks/ndcdtp4bQAaEjdIAPXuiHtX2qCP3ChQv62nw2m+17nFy3DwaD5lg6btkJzk6tPe0/V685BE6iJiZ4k8DQtDY2HH8tjh2rq7yWYHhmZ533wGEK/bC2T719+7ZeK/7rr7/Wq8/JGYBekbMC4XDYHJdKJf19NZtNyzU6dKBDp0MHGJtr6Ie5faqc5m8/Pd7doYdCob5duJUaQgeEjtABxuq2tcOKbARz6tSpnjU5Hd8u+1wuZ14Lt1pD6IDQETrAWN22try8rGm/Za37MSv59ddfzdP2Is6VlRUViUTMeiKR0JvCGMvNymz1QqGgx3KcMVvdag2hA0JH6ABjdduaMVnMuJAv16T7TSAbJrI+vFw3n5ub05PjPv300457xGVTmFQq1XFKXvZllwl7Mvu+fR15qzWEDggdoQOMhdBlktqlS5c0RjY2Nt57zM4qdOVy+b3FXuQxmYDXvT2rLEXb7953qzWEDggdoQNwDf2Akk6n9eI1rOUOgNAROiD0EY507N3dOUIHhI6UETogdILQAaEjdF5LgNAROkIHhI7QAaEjdISO0AGhI3RA6AgdoQMgdIQOCJ0gdEDogNABoSN0hA4IHaEDQkfoCB2hA0JH6IDQETpCB0DoCB0QOkHogNABoQNCP4rZ3d1VtVrN0RpCB4SO0AEQukN5/PixmpqaMrdT7RXZNU02bJH9zAOBQMeGLlZrCB0QOkIHQOgOZWtrS505c0ZvcdpP6PV6Xe9rXiwW9TgajZr7mlutIXRA6AgdAKE7FDkNLjKXvdFPnjzZV+iZTEb5/X5znM/ndcdtp4bQAaEjdACE7kBkj/LFxUWVzWb1eD+hJ5NJFQwGzbF03C6Xy1YNoQNCR+gACN2ByOnvL7/8Ur17905z4sQJlcvltES7E4/HVTgcNselUklNTk6qZrNpudYeecygO61Wy1HUxARvEhia1saG46/FsWN1ldcSDM/srPMe+NCEvrKyoubm5kxkUtzs3n/cixcvenbooVCobxdupUaHDnTodOgAdOgHkP1Ouctp+fZr4dLJG9fCrdYQOiB0hA6A0A9B6IlEQq3t/RKTNBoNPVu9UCjocSQSMWerW60hdEDoCB0AoR+C0L1er0qlUh33k8utbR6PR/l8PlWtVm3XEDogdIQOgNAPMOVyWS8Gs729/d7M+Eql0nfWvJUaQgeEjtABEPoBJZ1Oq1gsxlruAAgdoQNCH2Why/Ks3d05QgeEjpQROiB0gtABoSN0XkuA0BE6QgeEjtABoSN0hI7QAaEjdEDoCB2hAyB0hA4InSB0QOiA0AGhI3SEDggdoQNCR+gIHaEDQkfogNAROkIHQOgIHRA6QeiA0AGhA0I/rNRqNb12+yCbvu/u7urjnawhdEDoCB0AodvI69ev1cLCgpqbm9M7rZ06dUq9evWq7/Gya5ps2CL7mQcCAb08rN0aQgeEjtABELrNyDamP/30kzkOh8N6v/Jeqdfrel/zYrGox9Fo1NzX3GoNoQNCR+gACP0AcnHvzRaPx3vWMpmM8vv95jifz+uO204NoQNCR+gACN3B5HI5dfnyZXXhwgX19u3bnsckk0kVDAbNsXTcLpfLVg2hA0JH6AAI3cHINW6R+dLSUt8fVDp3OSVvpFQqqcnJSdVsNi3X2iOPGXRHJus5iZqY4E0CQ9Pa2HD8tTh2rK7yWoLhmZ113gMf+il3kW8oFOrbobfXurtwKzU6dKBDp0MHoEM/gDx48ED5fL6etWw223EtXE7TG9fCrdYQOiB0hA6A0B2IzHDf2trSH+/s7Kjz58+rVTkl9v9JJBJqbe+XmKTRaOjZ6oVCQY9lNrwxW91qDaEDQkfoAAjdgTx69EjfHy73oM/MzKjl5eWOSXFer1elUqmOa+1ut1t5PB7dycttb3ZrCB0QOkIHQOgOLa0qC8x0L/YiK8eJ7Le3tzsel06+Uqn0fC6rNYQOCB2hAyD0A0o6nVaxWIy13AEQOkIHhD7KQpeOvbs7R+iA0JEyQgeEThA6IHSEzmsJEDpCR+iA0BE6IHSEjtAROiB0hA4IHaEjdACEjtABoROEDggdEDogdISO0AGhI3RA6AgdoSN0QOgIHRA6QkfoAAgdoQNCJwgdEDogdEDoh5VarTbwqnC7u7v6eCdrCB0QOkIHQOg2IhuwLCwsqLm5OTU/P69WVlZUs9nse7zsmiYbtsh+5oFAoGNDF6s1hA4IHaEDIHQHOnPZE93ooJeWltT9+/d7Hluv1/W+5sViUY+j0ai5r7nVGkIHhI7QARD6AURkG4lEetYymYzy+/3mOJ/P647bTg2hA0JH6AAI/QDi9XrVt99+27OWTCZVMBg0x9Jxu1wuWzWEDggdoQMgdIdz584d5fF4+k6Oi8fjKhwOm+NSqaQmJyf1NXertfbIYwbdabVajqImJniTwNC0NjYcfy2OHaurvJZgeGZnnffAhyr058+fq5mZGbW5udn3GOm0Q6FQ3y7cSo0OHejQ6dAB6NAdytbWlp7l/uzZs32Py2azHdfCc7mceS3cag2hA0JH6AAI3YG8evVK37Ymku2VRCKh1vZ+iUkajYaerV4oFPRYJs8Zs9Wt1hA6IHSEDoDQHciDBw86rl0biESNSXKpVKrjfnK3262vtft8PlWtVm3XEDogdIQOgNAPeNEZWQyme5Lczs6OqlQqPT/Hag2hA0JH6AAI/YCSTqdVLBZjLXcAhI7QAaGPstBledZB13dH6IDQAaEDQicIHRA6QgdA6AgdoQNCR+iA0BE6QgdA6AgdEDpCR+iA0AGhA0InCB0QOkIHQOgIHaEDQkfogNAROkIHQOgIHRA6QkfogNABoQNCJwgdEDpCB0DoB5Hd3d2Bj6vVao7WEDogdIQOgNAdiPxwU1NTegOV/SK7psmGLbKfeSAQ0MvD2q0hdEDoCB0AoTsQ2Zvc5XLpbVP3E3q9Xtf7mheLRT2ORqPmvuZWawgdEDpCB0DoDkY6ZhH6fqfdM5mM8vv95jifz+uO204NoQNCR+gACP2QhZ5MJlUwGDTH0nFLZ2+nhtABoSN0AIR+yEKPx+MqHA6b41KppD+n2WxarrVHHjPoTqvVchQ1McGbBIamtbHh+Gtx7Fhd5bUEwzM767wHxr1DD4VCfbtwKzU6dKBDp0MHoEM/ZKFns9mOa+G5XM68Fm61htABoSN0AIR+CEJPJBJqbe+XmKTRaOjZ6oVCQY8jkYg5W91qDaEDQkfoAAjdochtZHNzc1ro8/Pzan193ax5vV6VSqU67id3u93K4/Eon8+nqtWq7RpCB4SO0AEQ+gGmXC7rxWC2t7c7Hpd71SuVSs/PsVpD6IDQEToAQj+gpNNpFYvFWMsdAKEjdEDooyx0ua7e3Z0jdEDoSBmhA0InCB0QOkLntQQIHaEjdEDoCB0QOkJH6AgdEDpCB4SO0BE6AEJH6IDQCUIHhA4IHRA6QkfogNAROiB0hI7QETogdIQOCB2hI3QAhI7QAaEThA4IHRA6IPQPKbKTW61WQ+iA0BE6AEIf1chua7LRi+yDHggE9LKyCB0QOkIHQOgjlHq9rvdDLxaLeizbtbIfOiB0hA6A0EcsmUxG+f1+c5zP53WnjtABoSN0AIQ+QkkmkyoYDJpj6dRdLhdCB4SO0AEQ+iglHo+rcDhsjkulkpqcnFTNZrPjOHnMoDutVstZ/vEPpXw+gKFoPX3q/Gtx3Lh/n9cSDP/eu37d8dciQrfYoYdCIcsdOiGEEPJHB6HvJZvNdlxDz+VyQ11DJ4QQQhD6EUij0dCz3AuFgh5HIpGhZrkTQgghCP2IRO5Dd7vdyuPxKJ/Pp6rVKv8pRzS95jAQQnjvIXRiZmdnR1UqFf4j+KVCCOG9h9AJ4ZcKIbz3CEInhBBCCEInhBBCEDohhBBCEDohhBBCEDohhBBCEDohhBCC0AkZkezu7qparcZ/BCF/0PuPIHRCbEdW8zt+/LheZz8QCKg3b97wn0LIIUW285yamtILcBGETojl1Ot1vd6+7IQniUajrLdPyCFF3muyA6UsLIPQETohtpLJZDp2xMvn8+yIR8ghRs6IidA57Y7QCbEV2bM+GAyaY/asJwShE4RORjDxeFyFw2FzXCqV9C+XZrPJfw4hCJ0gdDJKHXooFKJDJwShE4RORjnZbLbjGnoul+MaOiEInSB0MmppNBp6lnuhUNDjSCTCLHdCEDpB6GQUI/ehu91u5fF4lM/nU9Vqlf8UQg4hcpvo3NycFvr8/LxaX1/nPwWhE2Ivcg9spVLhP4IQQhA6IYQQgtAJIYQQgtAJIYQQgtAJIYQQgtAJIYQQhE4IIYQQhE4IIYQQhE4IIYQQhE4IIYQgdEIIIYQgdEIIIYQgdELIyGZ7e1t98cUXmpcvX9p6Ltlx77///W/f55FdwKSeyWTU5uam+u233ywdQwhCJ4SQrrx9+1bv0CU8evTI8vO0Wi21urqqn2d6evq9+qtXr5TX6zW/luD3+zs28hnkGEIQOiGEHJDQY7GY3jbXeJ5eQv/rX/9q1q5cuaJcLpceB4PBoY4hBKETQo5cvv76a/Xpp59qIRp5/vy5On/+vN7b+sSJE2ppaUmffq7X6/pYQcaXLl1SJ0+eVKFQSNeePHmij5XHvvrqK/P5pCad86lTp9SxY8dUIBBQ6XR6X6HfvHlTf52///3vqlarqWazqW7cuKHOnDmjn0P+TSaTuiuXyN7bZ8+eVQsLC/p55Jj2yCl042s8fPhQP5ZIJMzHyuXyQMcQgtAJIUcyImWR1fLysik+oyv95JNP1Llz57QcP//88w7xdtN9mlqQPwzk+rPP59PjmZkZ9be//c2sf/PNNz2F/uDBA3P8/fff62NE1sbXuXjxovroo4/0OJVKdfw88odEL6H/+9//Np+zWCzqx549e2Y+Jh8PcgwhCJ0QciQjopbTy9KRS+LxuJaXCFM6Y4lMNHv69GmHeEWcUv/ss8/0WE53y0Q0wThGutt2ORuSlG7akK503u3Pe/fuXf24fHzt2jV9fDab1ePjx4/r4yVra2v6MfljYRChSzdvfA3jenj79yoyH+QYQhA6IWQkIuI2BCadunTU9+7d0512r1PjhkDl9LyR2dlZ/Zj8cWCI9+OPPzbrL168MJ9HZpG3P6/Recupc2N2uZx+N2oej0cjlwJ6XSvvJ/TvvvvO/BqvX7/Wj/3yyy/mY//5z38GOoYQhE4IGYnINWnpVOX0ePvp86tXr/YUulyD7xa6XEM3hH79+nX98enTp816+7VqkXv78xqf2/41jOcQiUvX3s0gQv/xxx/N5zVuaWv/40W+j0GOIQShE0KOZO7cuaMntUkXbMhWOmMRu0hNrqEbwrYidOM0tnTScupe8sMPP5jP8+7du47nlS5YJtYZzykT6uQ6uSHparXa8f3LdfpBhC4dd/vlAolxi5t0/vJ9DHIMIQidEHIk0z0pTgQmp7RlRvnt27f1x8ZtW1aELhI0To+LqOV5jVPyspCMpPt5t7a21NTUlHlmQP4QkFP2xteR71Fm5cv1c5k5L7l165ZaXFxU8/Pz+jj5fBkLxnV3+Rl6TeIzvo9BjyEEoRNCjrzQpRuWyWftp9svXLig3rx5Y0noErkOLbeZtT+nCFK6715ClxjX3oVcLqcXfJEzCe3PIdf4DdEaP0cvjDMD8vVE2MYfC/KvfJ6sVGdkkGMIQeiEkJGInHKXe64LhYIpQycip8tlEpwdOcqyrCJ3mTG/s7Nj6TmkY/+9zx/kGEIQOiGEEEIQOiGEEEIQOiGEEILQCSGEEILQCSGEEILQCSGEEILQCSGEkDHP/wE4M1y0K8PdRwAAAABJRU5ErkJggg==\"/>","value":"#incanter_gorilla.render.ChartView{:content #object[org.jfree.chart.JFreeChart 0x22f611bf \"org.jfree.chart.JFreeChart@22f611bf\"], :opts nil}"}
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
;;; {"type":"html","content":"<img src=\"data:image/PNG;base64,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\"/>","value":"#incanter_gorilla.render.ChartView{:content #object[org.jfree.chart.JFreeChart 0x1d4c18a5 \"org.jfree.chart.JFreeChart@1d4c18a5\"], :opts nil}"}
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
;;; {"type":"html","content":"<img src=\"data:image/PNG;base64,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\"/>","value":"#incanter_gorilla.render.ChartView{:content #object[org.jfree.chart.JFreeChart 0xd6d85d3 \"org.jfree.chart.JFreeChart@d6d85d3\"], :opts nil}"}
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
;;; {"type":"html","content":"<img src=\"data:image/PNG;base64,iVBORw0KGgoAAAANSUhEUgAAAfQAAAE1CAYAAAARYhKbAAAa/ElEQVR42u3d7VOU193A8fxpnbYznWlftmn7ujP9A+43zf2iL5AIiEFBaoBWjXUCShJjCgoJxijGVpAWmjQQzV2ImhgUosiT8pg9t+e0uwMIanYXZOXzPfNrubjWZ8nHs3vt7ktBkiSVfC/5LZAkCeiSJAnokiQJ6JIkCeiSJAFdkiQBXZIkAV2SJAFdkiSg78i+++678O233xpjjDFbMkAHujHGGKAL6MYYY4AOdGOMMQboQDfGGGOADnRjjDFA3y5lMplw//79sLy8/MTbxfMzMzNFPQd0Y4wxQC+wCG17e3uorKwM+/btC//4xz82vG1/f3+oqqoKtbW1oampKczOzhZ8DujGGGOAXoROnToVjh8/nvtFxJ36es3NzYWKioowPj6ejuM/Ajo6Ogo6B3RjjDFAL0JTU1OhvLw8h+2TGhwcDI2NjbnjkZGRtOMu5BzQjTHGAL0IDQ0NpbvZP/jgg3D06NFw+vTpDe8O7+3tDc3Nzbnj+I+A+I+BQs6trKysLDfrPb5vjDHFmG8y34RTmVNmE2Y4M/xC/B0pSdB7enrC/v37wyeffBKuX7+e4D127Ni6t+3u7g6tra2544mJiYTv4uJi3ufs0I0xWz1np86Gl61NWe/MvmOH/jxBb2lpeQzb+fn5dXfoK2+7dheezzmgG2OADnSgF6GrV6+GhoaGx0B/8ODBunfPr3wsfHh4OPdYeL7ngG6MATrQgV6EHj58mJ5O9vXXX6fjixcvhtdff33VDr6rqyt9HHft8Wr1sbGxdNzW1pa7Wj3fc0A3xgAd6EAvUv/6179CdXV1qKurSzvn0dHR3Ln6+vrQ19e36vnk8fnq8bZxZz89PV3wOaAbY4AOdKAXqaWlpfQqcSuv7pucnEy794WFhcduG5/uttH3k885oBtjgA50oG9SAwMDobOz02u5G2OAbgG9lEGPz0dfuzsHujEG6BbQvdsa0I0xQAc60IFujDFABzrQgW6MMUAHOtCBbowBugV0oAPdGAN0oAMd6P4jZIwBOtCBDnRjjAE60IEOdGMM0C2gAx3oxhigAx3oAroxBuhABzrQjTEG6EAHOtCNMUC3gA50oBtjgA50oAvoxhigAx3oQDfGGKADHehAN8YA3QI60IFujAE60IEuoBtjgA50oAPdGGOADnSgA90YA3QL6EAHujEG6EAHuoBujAE60IG+RS0vL4eZmZmingO6MQboQAd6ge3duzeUlZXlpq6ubsPb9vf3h6qqqlBbWxuamprC7OxsweeAbowBOtCBXiTQb968mXbQcSKg6zU3NxcqKirC+Ph4Om5vbw8dHR0FnQO6MQboQAd6EUG/devWU283ODgYGhsbc8cjIyNpx13IOaAbY4AOdKAXEfTjx4+HM2fOhKGhoQ1v19vbG5qbm3PHccddXl5e0LmVrbzbf22ZTKbI8/Gj7/V/jcl7MplvN+HvpdmK6cn0oHeTVkem44X4O1KyoF+8eDFcuXIlXLhwIVRXV4fLly+ve7vu7u7Q2tqaO56YmEj4Li4u5n3uee3QZ2fjz+dlY/KeiYmrdrt26JYd+va9yj1evLby7vG1O/SWlpYNd+H5nAO6AboBOtCBvgldvXo1HDhwYN1z8e74ldgPDw/nHgvP9xzQDdAN0IEO9CJ079693AVxEc4TJ06Etra23Pmenp7Q1dWVPp6fn09Xq4+NjaXjeLvs1er5ngO6AboBOtCBXoRGR0fT4+Y1NTXp4rg33nhj1XPE6+vrQ19f36q75CsrK9Nz1RsaGsL09HTB54BugG6ADnSgF6F4Rd/k5ORjL/YSPxdfDGZhYWHV55eWlsLU1NS631e+54BugG6ADnSgb1IDAwOhs7Pzuf88gG6AboAOdKAXUNyxr92dA90YoAPdAnqJgb5dAroBugE60IEOdKAboBugAx3oQDcG6EC3gA50oBugG6ADHegCugG6ATrQgQ50oBugG6ADHehANwboQLeADnSgPzbTj2bUbMosAt0AHehAB/pWTYsv9k1b/wd0A3SgAx3oQAc60IFuAR3oQAc60A3QLaAL6BbQDdCBDnSgA90CeonPxMRnYX6+akvn0uIrvmo2abUv/c+W/3lOTl4BOtCBDnSgP++ZnOzZ8j+7Xl8zm7Y6n8PX4vT0+0AHOtCBDnSgW0AHOtCBDnQgA90COtCBbgHdAB3oQAc60C2gAx3oQAc60IEOdKAD3QI60IEOdKADHegW0IEOdAvoBuhABzrQgW4BHehABzrQi9Hy8nKYmZkp6jmgW0AHOtCBDvQi9cUXX4Rdu3aFW7dubXib/v7+UFVVFWpra0NTU9MjGGcLPgd0C+hABzrQgV6k7ty5Ew4ePBgqKys3BH1ubi5UVFSE8fHxdNze3h46OjoKOgd0C+hABzrQgV6k4t3gEfPR0dHw2muvbQj64OBgaGxszB2PjIykHXch54BuAR3oQAc60IvQ0tJSOHToUBgaGkrHTwK9t7c3NDc3547jjru8vLygc0C3gA50oAMd6EUo3v39/vvvh4cPH6bZu3dvGB4eToiurbu7O7S2tuaOJyYmQllZWVhcXMz73Mri57KztkwmU+RpBzrQC5pM5vYm/L3ciTMEdKAX+LV4qeh/L0sS9BMnToSamprcxIviqqurw1dffbXuDr2lpWXDXXg+5+zQLTt0O3SgA90OfRN60l3u8W75lY+Fx5189rHwfM8B3QI60IEOdKBvAeg9PT2hq6srfTw/P5+uVh8bG0vHbW1tuavV8z0HdAvoQAc60IG+BaDX19eHvr6+Vc8nj09tq6urCw0NDY9+I6cLPgd0C+hABzrQgb6JTU5OpheDWVhYeOzK+KmpqQ2vms/nHNAtoAMd6EAH+iY1MDAQOjs7n/vPA+gW0IEOdKADvYDiy7Ou3Z0DHehABzrQLaCXGOjbJaBbQAc60IEOdKADHehABzrQgQ50oAMd6EAHugX0TQI9vonKtWvXnmmADnSgAx3oQAf6NgX91KlTq173/EkDdKADHehABzrQgQ50oAMd6EC3gL5ZoMenkj148OCZBuhABzrQgQ50oJfIRXG3b98Of/3rX8O5c+ceG6ADHehABzrQgV4CoN+4cSO9Fam73IEOdKADHehAL2HQ33rrrQT37t270//HN1eJb6gSP96/fz/QgQ50oAMd6EAvBdDjO5fFHXp897JXX301XLp0KXfhXMQe6EAHOtCBDnSglwDo8e1IDx48mD6ura0Nx48fTx9/8MEHaZf+fd6mFOhABzrQgW4B/TmBfujQobB379708cmTJxPif/jDH8KuXbvSAB3oQAc60IEO9BIA/S9/+UtC/Ouvvw7Xr19PiGcviGtubnaXO9CBDnSgAx3opQB6JpNJ4GUbGRlJ72Ee38t8cXER6EAHOtCBDnSglwLo58+fD3/+858f+/xHH32UPr8Se6ADHehABzrQgb6Nn7YW72ZfW9ylx7vdp6amgA50oAMd6EAH+nYFvb+/P1y+fDk9bS3CHT/OTnd3d7pQLkI/Pz8PdKADHehABzrQtyvo8elqT3tjliNHjngMHehABzrQgQ707Qx6Y2NjqKqqyr3sa/w4O/HV4t57771w69YtoAMd6EAHOtCBXgqPoXd0dKTnnb/IAd0COtCBDvQd8W5rsaWlpXDnzp3w1VdfvTBvmwp0C+hABzrQdxToV65cCZWVlaseP4+v5T43N/fM38fMzMyjL6zJ9Lz2p7W8vJxuX8xzQLeADnSgA31Hg3716tUNL4qLqD+t+/fvp3dlq6mpSY+9HzhwINy9e/eJV9fHx+nj68Y3NTU9gnG24HNAt4AOdKADfceD/uabbya8u7q6ws2bN8Po6Gj45z//Gaqrq9PnFxYWnvjt42u9x2+XrbW1NbS1ta1727jjr6ioCOPj4+m4vb09PYZfyDmgW0AHOtCBDvT/Pn0tvv/52j788MME+u3bt7/3C9XE57Gv1+DgYLq6Plt8mdm44y7kHNAtoAMd6EAHevjPu63t3r077cyzxReTOXz48Pd6+9Th4eH0bm3x7Vc3uqiut7d31Ru+xB13fNpcIeeAbgEd6EAHOtDDf16zPfuYedz1xqewReDjcfZ90p/1leci5vEfAhv9YuLOPd4ln21iYiL9OPFNYPI9t7KVj/+vLV6sV9xpBzrQC5pM5vYm/L3ciTMEdKAX+LV4qeh/L58L6PHpatnd+MqJL/2azwvLRHxbWlo23KGvPLd2F57POTt0yw7dDh3oQLdDX7F7jY9Tnz17Npw5cyYB+vDhw7y+r08//TS9Pvx6DQ0NrXosPN5Nn30sPN9zQLeADnSgAx3oofC3T41XuMcXpMnu9uNV86dPn86d7+npSVfQZx+bj1erj42NpeN4NXz2avV8zwHdAjrQgQ50oIfC3z712rVrudd/37NnTzh69Oiqi+LiFfR9fX2rHmuPL2ITr66PO/mVF93lew7oFtCBDnSge/vUIrx9agQzvsDM2hd7ia8cF7Ff+1z2uJPf6B8K+Z4DugV0oAMd6N4+dZPePnVgYCDt9J93QLeADnSgA93bpxZQ3LE/7ZXmgA50oBugW0AvQt4+FegW0IEOdKC/QG+fCnSgAx3oQAc60Ev8aWvxyvQ4K5+ytvZzQAc60IEOdKADfZs/bS17EVz25eriy6xu9BKqQAc60IEOdF8zQN+GoMenqb399ttpsv3tb3977HNABzrQgQ50oAPdY+hABzrQgQ50C+hABzrQgW6ADnSgAx3oFtCBDnSgAx3oQAc60IEOdKADHehABzrQgW4BHehABzrQDdCBDnQB3QI60IEOdKADHehABzrQgQ50oAMd6EAHOtAtoAMd6EAHugE60IEuoFtABzrQgQ50oAMd6EAHOtCBDnSgAx3oQAe6BXSgAx3oQDdABzrQBXQL6EAHOtCBDnSgAx3oQAc60IEOdKADHehAt4BeyqDPzMyEhYWFZ7rt8vJyun0xzwHdAjrQgQ50oBfQ5ORk2L9/f6ipqQn79u0LJ06cCIuLixvevr+/P1RVVYXa2trQ1NT0CMbZgs8B3QI60IEOdKAXYWd+8+bN3A768OHD4ZNPPln3tnNzc6GioiKMj4+n4/b29tDR0VHQOaBbQAc60IEO9E0oYtvW1rbuucHBwdDY2Jg7HhkZSTvuQs4B3QI60IEOdKBvQvX19eHKlSvrnuvt7Q3Nzc2547jjLi8vL+gc0C2gAx3oQAd6kbt06VKoq6vb8OK47u7u0NramjuemJgIZWVl6TH3fM+tLH4uO2vLZDJFnnagA72gyWRub8Lfy504Q0AHeoFfi5eK/veypEG/ceNG2LNnT7h9+/aGt4k77ZaWlg134fmcs0O37NDt0IEOdDv0InXnzp10lfv169efeLuhoaFVj4UPDw/nHgvP9xzQLaADHehAB3oRunv3bnraWkR2vXp6ekJXV1f6eH5+Pl2tPjY2lo7jxXPZq9XzPQd0C+hABzrQgV6EPv3001WPXWcnIpq9SK6vr2/V88krKyvTY+0NDQ2PfiOnCz4HdAvoQAc60IG+yS86E18MZu1FcktLS2Fqamrdb5PvOaBbQAc60IEO9E1qYGAgdHZ2PvefB9AtoAMd6EAHegHFl2d91td3BzrQgW6AbgFdQLeADnSgAx3oQAc60IEOdKADHehABzrQgQ50C+hABzrQgW6AbgFdQLeADnSgAx3oQAe6BXSgAx3oQAc60IEOdKBbQAc60IEOdCAD3QI60IFuAd0AHehABzrQLaADHehABzrQgQ50oAPdAjrQgQ50oAMd6BbQgQ50C+gG6EAHOtCBbgEd6EAHOtCBDnSgAx3oFtCBDnSgAx3oQLeADnSgW0A3QAc60IEOdAvoQAc60IEOdKADHehABzrQgQ50oAMd6EC3gA50oAMd6AboQAc60IFuAR3oQAc60J+x5eXlZ77dzMxMUc8B3QI60IEOdKAXofgL2LVrV1haWnri7fr7+0NVVVWora0NTU1Nj2CcLfgc0C2gAx3oQAd6Eero6Ajl5eWhrKzsiaDPzc2FioqKMD4+no7b29vTty3kHNAtoAMd6EAHehGLO+YI+pPudh8cHAyNjY2545GRkbTjLuQc0C2gAx3oQAf6FoPe29sbmpubc8dxxx139oWcA7oFdKADHehA32LQu7u7Q2tra+54YmIifZvFxcW8z60sfi47a8tkMkWedqADvaDJZG5vwt/LnThDQAd6gV+Ll4r+93JH7NBbWlo23IXnc84O3bJDt0MHOtDt0LcY9KGhoVWPhQ8PD+ceC8/3HNAtoAMd6EAH+haA3tPTE7q6utLH8/Pz6Wr1sbGxdNzW1pa7Wj3fc0C3gA50oAMd6EUqPo2spqYmgb5v375w/vz53Ln6+vrQ19e36vnklZWVoa6uLjQ0NDz6jZwu+BzQLaADHehAB/omNjk5mV4MZmFhYdXn43PVp6am1v02+Z4DugV0oAMd6EDfpAYGBkJnZ+dz/3kA3QI60IEOdKAX+Lj62t050IEOdKAD3QJ6iYG+XQK6BXSgAx3oQAc60IEOdKADHehABzrQgQ50oFtABzrQgQ50A3SgA11At4AOdKADHehABzrQgQ50oAMd6EAHOtCBDnQL6EAHOtCBboAOdKAL6BbQgQ50oAMd6EAHOtCBDnSgAx3oQAc60IFuAR3oQAc60A3QgQ50Ad0COtCBDnSgAx3oQAc60IEOdKADHehABzrQLaADHehAB7oBOtCBLqBbQAc60IEOdKADHehABzrQgQ50oAMd6EAHugV0oAMd6EA3QLeALqBbQAc60IEOdKADHehABzrQgQ70rWt5eTnMzMwA3QK6ATrQgV6q9ff3h6qqqlBbWxuampoegToLdAvoBuhAB3opNTc3FyoqKsL4+Hg6bm9vDx0dHUC3gG6ADnSgl1KDg4OhsbExdzwyMpJ26kC3gG6ADnSgl1C9vb2hubk5dxx36uXl5UC3gG6ADnSgl1Ld3d2htbU1dzwxMRHKysrC4uLiqtvFz2Vn83vwaMa2dGbD9Uf/+7nZhFkM32z5n2cIy56CUpQWt/zPbj587etmk+ZhuPkcvhbnn/vf4h21Q29pacl7hy5J0nZux4A+NDS06jH04eHh7/UYuiRJQN8Gzc/Pp6vcx8bG0nFbW9v3uspdkiSgb5Pi89ArKytDXV1daGhoCNPT0/4GbOO25joGSb4egV6SLS0thampKX/y/gMiydcj0CX/AZF8PQrokiQJ6JIkCeiSJAFdkiQBXZIkAV2S9HwaHR1Nr+Xx4MEDvxlAlySVYmfOnAmHDh0KXV1deb0LmIAuSXrOZTKZUF1d7ZU1gS49e/Ed8I4dOxbOnTuX/gMSP46v6nfixIlQVVXltfel51D8+osvKtPU1BROnjzpNwTo0tOLj9Ht2rUrXLx4Md2td/jw4fSOeJ9//nm4devWqjfXkbR1X5cR9Pg1OTk56TcE6NKz/Ydj3759ueOPPvootLe3547/+Mc/hmvXrvmNkraweBFcBD3e9S6gS3mBHnfq8W1us8Ude3xfe0lAF9BVwqAfOXIk3f0uCegCuoAuCehAl4AuAV1AlyRJQJckSUCXJAnokiQJ6JIkCeiSJAnokiQBXZIkAV2SJAFdkiQBXZIkoEuSJKBLkiSgS9o5Xb16NZw6dSq8//77fjMkoEsq1bq7u9NbdK58+1xJQJcEdAnoknZGFy5cCG+88Ubo7OzMfe7evXuhpaUlVFdXh8bGxvDee++l27z11lu52ywuLoazZ8+GgwcPhoqKivT/vb29IZPJhLm5uXT7OIODg+HkyZMJ6Xh3+vj4eO77mJmZSd/n3r17w+uvvx4OHz78GOjP+uN8+eWX4eOPPw5Hjx4NCwsL/mAFdEk7q7fffjshGiHMAhpBjZ+LE7EtLy9/DNojR46kz9XX1yeUX3311XTc19cXHjx4kPv2a+fEiRPp23/33XcJ52L9OPEfH9mPHz586A9WQJe0s3r33XfD7t27w5tvvpmO+/v7czAODAyknfDau8KHhobScVVVVfoHQKyrqyt9rqGhYRW0586dC7Ozs+H06dPpuLKyMiwvL4fPPvssd5srV66kHyfeW5Dvj/OnP/0p/TzjLn1packfrIAuaWcXrzCPQO7ZsychG1sL+ocffpiO4265rq4uTdxhx8/FfxyshPbatWurcI4zPT2doI8fx7vRI/DF+nEkoEvSCtDjXdzZ1kLb0dGRu5v8zJkzj8160H7xxRerQI+Pi6+9e70YP44EdEk7skuXLqUL4OJuOBYvOMsi+e9//zvMz8+nu+VXQhsfv87uriPOK7tx48YzgR6/7+zx/fv3023Onz9f8I8jAV3SjmztRXHxgrL4OPd6F7RloY3I19bWps/V1NSkx8fjVfLxce0DBw48E+jxSvTsRXAR7GPHjj12UVw+P44EdElA/2+3b99Ou/Z4FXp8ylmceJv41LJsd+/eTbdZCX4EOT417VlAj12+fDns2rUrfS4Cffz48cfuhv++P44EdEn6b/Hu7OxzueOOvampKaH5zjvvPHbbeEFbRDc+vzyfq8vjLnxsbCx3Ad5GFfrjSECXtOOKO/G4c37ttddyO+h4pfno6KjfHAnokkqleIFcfF53vBs8vlJcvCjum2++8RsjAV2SJAFdkiQBXZIkoEuSJKBLkiSgS5IkoEsl8gX40ksv1EgCurRjQfdrkQR0CYJ+LZKALkHQr0UCuiQIAl0CuiSgSwK69EIiOPiUtVHxbUXv3bv3jD/q4FMmPPHHAboEdElPQfDlp6z1OnPmTPjZz34WfvWrX4Xf/va3YWJi4ik/6stPmfWL75X+wx/+MPd+6UCXgC6pSKDPzMyEn/zkJ+H69evpOL6PeV1dXdFBr62tDT/+8Y/DD37wA6BLQJdUbNAvXLgQfvOb3+SO//73v4df/vKXm7JDjzv/CPri4iLQJaBLKiboJ0+eDL/73e9yx/Fu8R/96Echk8kAXQK6pFIB/dixY+H3v/997nh0dDShOzc3B3QJ6JJKBfR33303vPLKK7nj+Fh6fKz7yQFdArqkbQV6d3e3x9AlAV3abqCXPWWtbXZ2Nl3l/uWXX6bj6urqdEX6kyt7ygBdArqkgkDPp9OnT4ef/vSn4de//nXard+9e7foP8+amprw85//PIH+i1/8Ihw5cgToEtAlFRvB+NzwsbGxF+LXIgno0o4F3a9Fkq9ACYJ+LRLQJUEQ6BLQJaD7tUgCuvQiIPgijSSgS5KkAvp/t1jrRwTkZLAAAAAASUVORK5CYII=\"/>","value":"#incanter_gorilla.render.ChartView{:content #object[org.jfree.chart.JFreeChart 0xd6d85d3 \"org.jfree.chart.JFreeChart@d6d85d3\"], :opts nil}"}
;; <=

;; **
;;; *3.  What does grouped bar chart reveal about smoking habits and gender?*
;; **

;; @@

;; @@
