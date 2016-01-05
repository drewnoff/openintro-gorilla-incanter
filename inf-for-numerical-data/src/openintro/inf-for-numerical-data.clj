;; gorilla-repl.fileformat = 1

;; **
;;; # Inference for numerical data
;;; 
;;; Original lab is available [here](http://htmlpreview.github.io/?https://github.com/andrewpbray/oiLabs-base-R/blob/master/inf_for_numerical_data/inf_for_numerical_data.html).
;;; You can download the data for this lab from [here](https://www.openintro.org/stat/data/?data=nc). 
;; **

;; @@
(ns openintro.inf-for-numerical-data)

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
;;; ## North Carolina births
;;; 
;;; In 2004, the state of North Carolina released a large data set containing 
;;; information on births recorded in this state. This data set is useful to 
;;; researchers studying the relation between habits and practices of expectant 
;;; mothers and the birth of their children. We will work with a random sample of 
;;; observations from this data set.
;; **

;; **
;;; ## Exploratory analysis
;; **

;; **
;;; Load the `nc` data set into our workspace.
;; **

;; @@
(def nc (iio/read-dataset "../data/nc.csv" :header true))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;openintro.inf-for-numerical-data/nc</span>","value":"#'openintro.inf-for-numerical-data/nc"}
;; <=

;; **
;;; We have observations on 13 different variables, some categorical and some 
;;; numerical. The meaning of each variable is as follows.
;;; 
;;; variable         | description
;;; ---------------- | -----------
;;; `fage`           | father's age in years.
;;; `mage`           | mother's age in years.
;;; `mature`         | maturity status of mother.
;;; `weeks`          | length of pregnancy in weeks.
;;; `premie`         | whether the birth was classified as premature (premie) or full-term.
;;; `visits`         | number of hospital visits during pregnancy.
;;; `marital`        | whether mother is `married` or `not married` at birth.
;;; `gained`         | weight gained by mother during pregnancy in pounds.
;;; `weight`         | weight of the baby at birth in pounds.
;;; `lowbirthweight` | whether baby was classified as low birthweight (`low`) or not (`not low`).
;;; `gender`         | gender of the baby, `female` or `male`.
;;; `habit`          | status of the mother as a `nonsmoker` or a `smoker`.
;;; `whitemom`       | whether mom is `white` or `not white`.
;;; 
;;; *1.  What are the cases in this data set? How many cases are there in our sample?*
;; **

;; **
;;; As a first step in the analysis, we should consider summaries of the data. For categorical columns this
;;; can be done using the `category-col-summarizer` function and for numerical columns we can use `quantile` function:
;; **

;; @@
(doall
  (map println
       (map #(s/category-col-summarizer % nc)
            [:mature :premie :marital :gender :habit :whitemom])))

(println (clojure.string/join "\t" ["col" "Min." "1st Qu." "Median" "3rd Qu" "Max."]))
(doall
  (map #(println (clojure.string/join "\t" %))
       (map #(conj (s/quantile (filter (partial not= "NA") (i/sel nc :cols %))) %)
            [:fage :mage :weeks :visits :gained :weight])))
;; @@
;; ->
;;; {:col :mature, :count 0, :is-numeric false, younger mom 867, mature mom 133}
;;; {:col :premie, :count 0, :is-numeric false, full term 846, premie 152, NA 2}
;;; {:col :marital, :count 0, :is-numeric false, not married 613, married 386, NA 1}
;;; {:col :gender, :count 0, :is-numeric false, female 503, male 497}
;;; {:col :habit, :count 0, :is-numeric false, nonsmoker 873, smoker 126, NA 1}
;;; {:col :whitemom, :count 0, :is-numeric false, white 714, not white 284, NA 2}
;;; col	Min.	1st Qu.	Median	3rd Qu	Max.
;;; :fage	14.0	25.0	30.0	35.0	55.0
;;; :mage	13.0	22.0	27.0	32.0	50.0
;;; :weeks	20.0	37.0	39.0	40.0	45.0
;;; :visits	0.0	10.0	12.0	15.0	30.0
;;; :gained	0.0	20.0	30.0	38.0	85.0
;;; :weight	1.0	6.38	7.31	8.06	11.75
;;; 
;; <-
;; =>
;;; {"type":"list-like","open":"<span class='clj-lazy-seq'>(</span>","close":"<span class='clj-lazy-seq'>)</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"},{"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"},{"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"},{"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"},{"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"},{"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}],"value":"(nil nil nil nil nil nil)"}
;; <=

;; **
;;; For numerical variables, are there outliers? If you 
;;; aren't sure or want to take a closer look at the data, make a graph.
;;; 
;;; Consider the possible relationship between a mother's smoking habit and the 
;;; weight of her baby. Plotting the data is a useful first step because it helps 
;;; us quickly visualize trends, identify strong associations, and develop research
;;; questions.
;;; 
;;; *2.  Make a side-by-side boxplot of `habit` and `weight`. What does the plot 
;;; highlight about the relationship between these two variables?*
;;; 
;;; The box plots show how the medians of the two distributions compare, but we can
;;; also compare the means of the distributions using the following function to 
;;; split the `weight` variable into the `habit` groups, then take the mean of each
;;; using the `mean` function.
;; **

;; @@
(print (i/$rollup s/mean :weight [:habit] nc))
;; @@
;; ->
;;; 
;;; |    :habit |           :weight |
;;; |-----------+-------------------|
;;; | nonsmoker | 7.144272623138631 |
;;; |    smoker | 6.828730158730158 |
;;; |        NA |              3.63 |
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; **
;;; There is an observed difference, but is this difference statistically 
;;; significant? In order to answer this question we will conduct a hypothesis test.
;; **

;; @@

;; @@
