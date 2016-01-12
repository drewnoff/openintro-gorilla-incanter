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
;;; 
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

;; **
;;; ## Inference
;; **

;; **
;;; *3.  Check if the conditions necessary for inference are satisfied. Note that 
;;; you will need to obtain sample sizes to check the conditions. You can compute 
;;; the group size using the same `$rollup` function above but replacing `mean` with 
;;; `count`.*
;;; 
;;; *4.  Write the hypotheses for testing if the average weights of babies born to 
;;; smoking and non-smoking mothers are different.*
;;; 
;;; Next, we introduce a new function, `t-test`, that we will use for conducting
;;; hypothesis tests and constructing confidence intervals. 
;; **

;; @@
(def test-res
  (s/t-test (i/sel (i/$where {:habit "nonsmoker"} nc) :cols :weight)
            :y (i/sel (i/$where {:habit "smoker"} nc) :cols :weight)
            :paired false
            :conf-level 0.95
            :alternative :two-sided))
(print test-res)
;; @@
;; ->
;;; {:p-value 0.019450556443720846, :df 171.32465666242578, :n2 126, :x-mean 7.144272623138631, :y-mean 6.828730158730158, :x-var 2.306390783389556, :conf-int [0.05150536718699117 0.5795795616299553], :y-var 1.9214943746031747, :t-stat 2.359010944933727, :n1 873}
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; **
;;; We supply two sets of data (one as first arg and second as `:y` option)
;;; and set up `:paired` option to `false` because
;;; we are interesting in hypothesis tests based on a difference in means.
;;; `:conf-level` option corresponds to the returned confidence interval.
;;; The `:alternative` hypothesis can be `:less`, `:greater`, or `two-sided`.
;; **

;; **
;;; To depict the p-value, we draw the distribution of the point estimate as though @@H_0@@ were true and mark areas representing at least as much evidence against @@H_0@@ as what was observed. Both tails are shaded because it is a two-sided test.
;; **

;; @@
(def df (test-res :df))
(def t-stat (test-res :t-stat))

(def w-pdf
  (partial dist/pdf (dist/t-distribution df)))

(def x-lrange [-5 (- t-stat)])
(def x-rrange [t-stat 5])

(def p-chart
  (c/function-plot w-pdf (first x-lrange) (second x-rrange)
                   :y-label "t-distribution PDF"
                   :x-label "t*"))

(c/add-function p-chart w-pdf (first x-lrange) (second x-lrange))
(c/add-function p-chart w-pdf (first x-rrange) (second x-rrange))


(chart-view p-chart)
;; @@
;; =>
;;; {"type":"html","content":"<img src=\"data:image/PNG;base64,iVBORw0KGgoAAAANSUhEUgAAAfQAAAE1CAYAAAARYhKbAAAt0UlEQVR42u2dC1sUR9qG908m7s9REJAzKAJeKpoAgllXAUU3G0TQSMDIKlcg4IZRMYhAADnpILDWN2+Znm8gc+iePlX33E+uvsLA0N68VdXP1OmtvymEEEIIRV5/IwQIIYQQho4QQgghDB0hhBBCGDpCCCGEMHSEEEIIQ0cIIYQQho4QQgghDB0hhBBCGDpCCCGEocdW//vf/9Qff/zhyfX582fP7uX1ZSobXHARM7jg+v8LQ8fQaQxwwUXM4MLQMXQeanDBRd2HCy4MHUPnoQYXXNR9uODC0DF0GgNccBEzuODC0DF0GgNccBEzuDB0E3R4eKh2dnZCuw+GDhdc1H244MLQXWpyclJVVVWpxsZG1d7ernZ3dwv+zosXL9SpU6fU4uKiq/tg6HDBhaHDBReG7oGSyaSqqKhQq6ur+vXAwIAaHBzM+zvLy8uqpaVFVVZWpg29mPtg6HDBhaHDBReG7pGmp6dVW1tb+nUikdA97FyS4XQx86WlJVVbW5s2dKf3wdDhggtDhwsuDN1DjY+Pq66urvRr6WGXlZVlfe/BwYG6ePGimpmZ0a8zDd3ufU6ePJm+jksKxIvLy3t5fZnKBldMuPb21OdkkpjBBZcH94ucoY+MjKje3t70642NDW22+/v7f3mvDKPfu3dPffz4UV81NTVqfn5e966d3IceOlxweX9tPn6s1Fdf6WtzbIyYwQVXKfbQu7u7bfXQe3p6VF1dXfqSRXHV1dXqzZs3ju6DocMFlzfX2tu3aROX6/P0tDbzzO/Je4gZXHCVgKHL8Hnm3Lf0uO3OfWcOubu5D4YOF1zueuSWuVtclomb1GOnLOHC0H3W3t6eXp2+srKiX/f39x9Znd7X16empqYKGnqh+2DoNFK4PHzY5DDqbFyZPXbKEi64SmAfumxBa2pqUq2trWp7ezv9s4aGBjU8PFzQ0AvdB0OnkcLlzbU1OqrNef3VK9tc8l75HfldyhIuuGKeKU5WsG9tbYV2HwwdLrgKX9v37yt14kROY87HpT8IpH5X7kFZwgVXjA2dXO40BrjM5kob8tBQ0VyFPhBQlnDBhaFj6DQGuAJYzV7IiO1wWUP2Qa9+pyzhwtAxdAwdrpLnWk8kvpjw0pJrLrmHnoNP3ZOyhAsuDB1DpzHAFeC/lzx/Xn1sa/OMS+71saWFsoQLLgwdQ6cxwBXY3Pmf894bs7Oeccm99Hz88DBlCRdcGDqGTmOAK7BV7TZXpjvhEjMPcoEcdQwuDB1Dx9DhKkmudC/awTYzp1xBLpCjjsGFoWPoGDpcJclVzDx3MVxBZZCjjsGFoWPoGDpcJcdV7Ep0p1zp7XAPH1KWcMGFoWPoNAa4wtqm5gWXmLndRXeUJVwYOoaOodNI4fJhm5pXXPrfS12UJVxwYegYOo0BLq8Ww83Nfekxp/4fFJebf5OyhAtDx9AxdLjg8ri37Iar2FEByhIuDB1Dx9DhgsvjnrIbrmLn7SlLuDB0DB1DhwuubFvVXMxlu+XycwsbdQwuDB1Dx9DhKgkuLw5NccPl9xY26hhcGDqGjqHDVRJcXgx5u+XycwsbdQwuDB1Dx9DhKgkuLxalecHl1xY26hhcGDqGjqHDFXsur7aNecHl1xY26hhcGHpAOjw8VDs7O7beK+/b3NzUAcTQaQxw/WFMr9grLj966dQxuDD0ADQ5OamqqqpUY2Ojam9vV7u7u1nf9/79e9XQ0KDq6upUbW2tam5uVmtra+mf19TUqJMnT6avpqYmDJ1GCleAPWKvuPzopVPH4MLQfVYymVQVFRVqdXVVvx4YGFCDg4NZ37u9va0WFhbSr3t7e1V/f/8RQ5efS29fLjFpDJ1GCpe/W9X84vI60Qx1DC4M3WdNT0+rtlSjtZRIJHRP3Y6uX7+uRkZGjhj64uIiQ+40BrgC3KrmV7y8TjRDHYMLQ/dZ4+PjqqurK/1aeuplZWV5f2d+fl7duHFDXbt2TX348OGIocv37ty5o2ZmZrL+buaQ/HFJgXhxeXkvry9T2eAKj0tM09R4mcwGF1y+1v0oGrr0sGXo3NLGxoY22/39/bxz7mLcly5dOvJJZnR0VE1MTKgHDx6o6upqNTY2Rg+dT91wBZidzfNeisFscMFFDz1LD727u9tRDz3zw0Dm7x43/cyhfAydRgpXtIa1TZ4OoI7BhaFnkQyNZxqvDKfbnUOfmppSra2tWX82OzurV8Fj6DRSuKK78MzUBXvUMbgw9Cza29vTq9xXVlb0a1m1nrnKva+vTxu3SFawLy8v668PDg5UZ2enun37tn69vr6eXhAn5tzT03NkBTyGTiOFK3pbw0zcUkcdgwtDL7APvbKyUu8blx63bE+zJPvOh4eH9ddzqUYt+9VlD/qZM2fUlStX0ovilpaW9Ly57FGXxXEdHR0597Nj6HDBFZ3kLaYlvaGOwYWhF5D0uLe2tmwZrySYyWbWElDJIOfEyDF0uEqRK0rpVU1KS0sdgwtDJ5c7jQEuo7i8nJsOIl6mHBxDHYMLQ8fQaQxwGcPl9erxIOJlwtGu1DG4MHQMncYAl3FcXu7vDipebpmpY3Bh6Bg6hg4Xho6hU8fgwtAxdBoDXGZxeZ1MJoh4eTFNQB2DC0PH0DF0uGLF5XUymaDi5XYhH3UMLgwdQ8fQ4YoN1/rLl1+2gL14Ebl4raeYhV3+BsoSLgwdQ8fQaaQlzbU9OKg+nToV2XgJu/wNlCVcGDqGjqHTSEuay40hmhAvNx9IqGNwYegYOoYOVyy43A5ZmxAvPWXw9ddFTRlQx+DC0DF0DB2uWHD5kbs9jHgVu6iPOgYXho6hY+hwRZ7L78VwQcar2G131DG4MHQMHUOHK/Jcfi+GCzpexSSZoY7BhaFj6Bg6XJHn+lRe7utiOAydOgYXho6h0xjg8plr7d27L0PUv/8ei3gVmzWOOgYXhp5Fa2trqqmpSf3jH//Qr1dXV9XQ0JCamZnB0DF0uAzk8it3e1jxKiZrHHUMLgw9i1ZWVtTJkydVR0eHfj07O6tf37p1C0PH0OEyrYee6pnrHnqqpx6XeBWzBY86BheGjqFj6HBFmksviCsvj128nCbJoY7BhaEHZOiHh4dqZ2fH1nvlfZubmzqAbu6DocNVClyfysoCWRAXdLycrtynjsGFoecx9Lq6OnX79m3V2dmpX587d06/zrzsaHJyUlVVVanGxkbV3t6udnd3s77v/fv3qqGhQf+7tbW1qrm5Wc/nO70Phg5XqXCtv3qlM6vJ/+MWL6d/G3UMLgw9j6HbuQopmUyqiooKvbBONDAwoAZTn7yzaXt7Wy0sLKRf9/b2qv7+fsf3wdDhKhWuoPafhxUvJ6MP1DG4MPQsEtOsrKy0dRXS9PS0amtrS79OJBK6h21H169fVyMjI67vg6HDFVcuvw9jCTteTtYHUMfgwtB91vj4uOrq6jryYaEs9ak7n+bn59WNGzfUtWvX1IcPH4q+D4YOV5y5gjiMJex4OVnBTx2DC0O3qb29PXVwcOD496SHLUPnljY2NvRQ/f7+ft45dzHzS5cupf9wu/fJNx0gBeLF5eW9vL5MZYPLey519apSHR2xj5cYOnUMrrhxBW7oYuDff/+9am1tVadOnVLl5eV65fuTJ08c9dC7u7uL6lmLiVu/6+Y+9NDhihtXsdnUItdDd5AFjzoGFz30PD3yK1eu5FwMJwvS7HzKkOxymXPfMpxud+57ampKf5hwex8MHa44cgWVHS7seNnNU08dgwtDz6F//etfafOWoe979+7pbWrnz59Pf18M184HA1mdLivnRbJqPXN1el9fX/o+ssJ9eXk5PTog2+WsrXGF7oOh00gx9HjGy+5KfuoYXBh6Fgl4TU2NNu2HDx/+5WdipvKzb775xvY+dFkRL/nhpcct29Msyb7z4eFh/fXc3JzeZy570M+cOaNHCKxFcYXug6HTSEtqyD3AdK9hx8vufnTqGFwYehZJwhYxbDHbbBKTlTl1MV4n8/FbW1u2jFcSzORKGmP3Phg6XHHmCjLdqwnxsrMfnToGF4aeRfKPFeqBV1dXO1qURi53GgNc3nEFvf887HjZGXanjsGFoefJFFdfX68zsmW7Tp8+bStTHIZOY4DLW64w9p+HHS/5W2XYfSP1t1PH4MLQQ0r9iqHTGODylit5/rzjs8LjEK9Cfzd1DC4M3efUrxg6jQEu77h0TzXVO8/XU41rvAqNTFDH4MLQS0QYOlxx4Ar6MBbT4pVv7QB1DC4MPc/WNdmyJnvQ5ahS2ZcuJ55h6Bg6XOFx2U2yEtd45VvdTx2DC0PPIUn5eny+vKWlpah87hg6jQEu91xO0qDGNV759t9Tx+DC0LPo8PBQZ2UTE6+rq1MXLlxIm/rTp08xdAwdrpC4wsgOZ1q8csWAOgYXhp5FkthFzFsSx3z69El/b3R0VH9PUrVi6Bg6XBg6hk7dhytC29YyE8u8fv1af++7777D0DF0uMIYcg8p3atRQ+55ph2oY3Bh6DYTy8j55PI9yaMuudytC0OnMcAVDFdY6V5Ni1euhYHUMbgwdBLLYOhwRYLLTj7zUohXrq171DG4MHQSy2DocBnPZffEsVKIV65YUMfgwtBJLIOhw2U8V9gJZUyLV7bRCuoYXBg6ho6hw2U8lwnD7SbFK9sHHOoYXBg6ho6hw2U0lynD7SbFK1tMqGNwYegYOoYOl9Fcpgy3mxav46MW1DG4MHQMHUOHy2iusPO3mxqv49v4qGNwYegODHF7e/svF4ZOY4DLPy4T8rebGq/jiXaoY3Bh6AW0ubmprl+/rqqqqlztQ5f88Ds7O7beK++zUs5i6DSGUucKO92ryfHKjA11DC4MvYhT15wa+uTkpP5A0NjYqI9i3d3dzfnhoaGhQR8II1nqenp61P7+fvrnNTU1R/5tyVqHodNIMXQMnToGF4ZuQ21tbdo8b968qWZmZtSLFy+OXIUkZ6jLyW2SrEYkaWQHBwdz9swXFhbSPXo5i/358+dHDF1+Lj+TS0waQ6eRxplrPZH4Mqy8tES8jg+5p2IisZEYUcfgwtBt6O7du9rQ19fXi/r96elp/aHAUiLV+KSnbkdi/pn54sXQFxcXGXKnMZQMV/L8efUx1X6IV/ZLYvMxFSPqGFwYug3Nz8+rsrIyde/ePTU3N/eXq5DGx8dVV1fXkbSycj87Onv2rJqYmDhi6HJIzJ07d/RoAYZOI40z1/rLl0qdOKE2XrwgXrn2o8soYSpGEivqGFwYegHJgjg3c+gjIyOqt7c3/XpjY0P/XubceDY9evRIz5FnLo6TM9nF4B88eKCqq6vV2NjYX34vH5sUiBeXl/fy+jKVDa4iuFL1XVVVEa9CTBIjiRV1DK4IcUXS0KWH3t3d7aiHLmevnzlzRr2TLTt5FtplDuXTQ+dTd9y4TEn3anq8rMQ71DG46KEX0N7envrw4UPOq5BkaDzTeGUIP98c+vLysl7l/kpSO+bR7Oysam5uxtBppPHk2tw0Jt2r6fGy0sBKzKhjcGHoNnRwcKDN9s2bN7aMPPMDgaxylzPWRbLILXOVe19fn5qamtJfr62t6W1rYvrHJYvyrAVxYs6ypS1zwRyGTiONFdfoqDHpXqMQLxnNkJhRx+DC0AtI5q3l7PPMoXbZxiZb0uzuQ5fflznx1tbWIxnmxMCHh4f112Ls2Yb1xYyXlpb0vLn03mVxXEdHR8797Bg6XFHnknlh04bbTY6XxCr1gKCOwYWhFxrazjV/LqbupIe/tbXlikUCKslnnBg5hg5XJIeQZeW2YcPtJpfj8TSwxAwuDD2LOjs7tXlLxjhJ6iI95WfPnunesnzf6xSt5HKnMbDIa1D30IlX8VnjiBlcGHoWyTC57Ac/rh9++EEber6V6Bg6jQGuIuaDy8uZD8bQ4cLQvTf0ixcvqtOnT+ueeeZCN0nLKoYelRPXMHS4osBlna72+cMH4lVE3Ew5lY66D5eRhn7//v30nLlsNzt37pw2eHnd0tLC8ak0Brh86GkSryLiVl3NQkK4MPRCi9ms3njm5SavOoZOY4ALQ2erH1wYegjb1uQPkUNW/v3vf+s86pL97ePHjypKwtDhisSQ+5+rtT9/+kS8SMYDF4bu3tBlNbusZJftYW/fvs16KIvdw1kwdBoDXM5WuOtFccSrKDbS5cKFoR+T7C+XYXXJCuc2lzuGTmOAy1nGMzEk4lUcm5XXnZjBhaFj6Bg6XOEmlPlzyJh4FceWGUNiBheGntLOzo7Oqy4L4twezoKh0xjgcjDc/mfvkngVz2basDtlCZcxi+Ikz/rVq1ezbmeT74tRYug0Brg8GG5PmbllRMSreDZrHQIxgwtDPyYZcj+VetAc1927d/WQu9v87Bg6jQGuP9T6ixdf8re/fEm8XLKZltedsoQrdEOXE9LGxsb06Whi3PK1dY2MjOh96GL0MiSPodMY4HJ3fWxrUx/PnydeHrGZlAaWsoQrdEOXHO75FsPJdfnyZebQaQxwud17LmmVUwa0nkgQLy966IalgaUs4Qrd0NtSPYaqqipVVlamzVu+tq7a2lp169YtMsXRGODyMDsc8fKOTebQTVkYR1nCZcwc+mCqUUj+9qgLQ4cLQy+dum/SfnTKEi5jDP3w8FDt7+/nvDB0GgNcLofcsyziIl7u2Ezaj05ZwmXUKncSy2DocPm8//zYNivi5Z7NlP3olCVcGDqGTmMoEa5sxkO83LOZMuxOWcJljKFL+tdffvnlyPX06VO9ZU1OXnMydC8Z6Oxmqvv06ZPr+2DocJnOlWtomHi5ZzNl2J2yhMsYQ8+l8+fPq8bGRtt72mV1vLy/vb1dn+KWTZubm6qhoUHV1dWp+vp61dPTc2Se3u59MHS4osKVqxdJvLxhM2HYnbKEyxhDX1lZUS9fvkxfiURC/fzzz+r06dN6yH1jYyPv7yeTSVVRUaFWV1f164GBAb1yPlfPXI5utXrily5dUs+fP3d8Hwwdrqhw5TIc4uUNmwnD7pQlXJGYQ5fetPyR+TQ9Pa33tFuSDwR2e/Zi2v39/a7vg6HDZSJXviFh4uUNmwnD7pQlXMYb+oULF9TMzEzB3x8fH1ddXV3p19LDlmQ1dnT27Fk1MTHh+j4YOlwmcuXrPRIv79jCHnanLOEyxtBlGHx9ff3I5eTYVMn73tvbm34tQ/TygaDQHvZHjx7p9LPW4ji798m3Al8KxIvLy3t5fZnKBleWf7u6Wn0eHSVefrOlYqxjTczgMogrtEVx8o+/fv1aPXv2TF+/SyIMm5KedXd3t6OetfxbZ86cUe8kJ7OL+9BDh8tUrkL5xomXd2xhn75GWcJlTA/9/fv3OvXr8SH3ixcv6lXphSTD8plz3/Pz83nnvpeXl/Uq91cy9+XiPhg6XKZz5TsRjHh5yxbm6WuUJVzGGLpsHcu1KE5WoReSHK8qq9NltbxIFrllrk7v6+tTU1NT+uu1tTW90E7M2ul9MHQaKYaOoWPocGHoBfabi3kPDQ3peevt7W097G6Zup35dNk/XllZqefE5Xx1uYclMfDh4WH9tRh7tg8OYsaF7oOh00ijxCVHpeph4KUl4uX3kHuW42mJGVwlaei3b9/Ouviss7NTf196znZ0cHCgtra2XPMUex8MHS6TuJKpD8of29qIV0BsEuuPqZgTM7hKztCXUp9o5+bm9CW9Yknz+vjx4/T35JLhduklk8udxgCXw975y5dKnTihNl68IF4Bsa2nYi0xl9gTM7hKytBv3ryZ90AWDmfB0OHyZ/858fKPTWIexn50yhIuDB1DpzHElMtOohPi5T1bWGlgKUu4QjV0SeYii93sXBg6jQEub9K9Ei9/2cJKA0tZwhWqocsBKbKSXU4ze/v27ZG58+MXhk5jgMvb4Xbi5R9bGGlgKUu4jBhyl7PQ8x3OwpA7jQEuh8Pt5eW2DIV4+cOmP1ClyoCYwYWhY+gYOlxFX4XSvRIv/9nCSANLWcIVqqHLgSyStU32fEvyFjmQRdK8MoeOocPlX3Y44hUMW9BZ4yhLuIxJLGP10H/44QcVZWHocIXeQ3fQOyRePvXQHYySUPfhip2hP336VBv6999/j6Fj6HC5XRBnc/6WePnHZncdA3UfrtgZuhyVWl9fr7PCscodQ4fL3/3nxMt/tqD3o1OWcBk35M6iOAwdLv/3nxMv/9mC3o9OWcKFoWPoNIYYcTntFVKO/rIFuR+dsoTLGEOX09TIFIehw+VyuN1hHnHK0V+2IIfdKUu4jDF0Oav86tWrf/n+/fv39fets8oxdBoDXDmGeIs46Yty9JdNn3j39dd5T7yj7sMVyyF3OT71uO7evauH3L044xxDpzHEmauYs7gpR//ZCp1JT92HKzaGLuegj42N6dXtYtzytXWNjIyompoabfQyJI+h0xjgyrHveWlJ73teTySIl2FsUiZ6T3qqjKj7cMXa0JuamgoenXr58mXm0GkMcPmQmYxyDIYtiKxxlCVcoRt6W1ubqqqqUmVlZdq85Wvrqq2tVbdu3VKLi4sYOo0BLgwdQ6cs4YrCHPrg4KA6d+6c6/scHh7qHPFO3o+h0xiizlXssC7l6D9bsdMh1H24Imvo//3vf9U///lPNTs7q/+ggYEBVVdXp7q6uvR56Xbn46Vn39jYqNrb2wv+nvyxMj8vh8NkSubtM4f8ZVoAQ6eRmsxV7MIryjEYtmIWLFKWcEXW0K9du6aH3cWEf/755yOGKj8rpGQyqSoqKtTq6qp+LR8IpNefb0TAGubPZugLCwu69y6Xky1zGDpcQXPprVEnThS1NYpyDIatmC2FlCVckTX0lpYWdeHCBf217DsXo5X/Nzc3668LrXKfnp7W8/GWEomE7qnnk3x4kHsfH3YXQy923h5DhytoLjfJSyjH4NicJv2hLOGKrKGLGTc0NOiscKdPn9ZGKwe2PHz4UH+9srKS9/fHx8f18Lwl6alLD7xYQ5dRgTt37qiZmRkMnUZqNJcbo6Acg2PzO2scZQmXMYZ+8+ZNba7l5eX6/5WVldpo5Xx0ef3+/fu8vy971nt7e9OvNzY29O/t7+87NvTR0VE1MTGhHjx4oKqrq/We+OPKl2deCsSLy8t7eX2ZylZyXPJB98QJ9TnVPoiX4WzyDJOySnU2qPtwBcUViqEvLy+ne+Zy/fjjj7q3K0PustCt0Dy29NC7u7s96aEfX2iXOZRPD51P3SZx6cVwLhZbUY7BsvmZNY6yhMuYHrpIhtWldywr3uWPWlpaUrdv31ZPnjwp+LsyNJ5pvPPz80XPoWdKVt3LhwoMnUZqGpebxXCUYzhsfmaNoyzhCtXQZSX5s2fPtLG+fftWzc3N5bzsnNYmq9ytufb+/v4jq9z7+vrU1NRUQUNfX19PL4gTc+7p6dH3wtBppKZxeTEnSzkGz+ZXkhnKEq5QDd2aN3/z5o0n56HL8LjMvcu+cckNv729nf6ZLLiTE90sWfvc5d719fXpn8mogMyby89kcVxHR4ftffAYOlxBcn0qL3e9appyxNBpkxi6kYYukj3lbk9mk4Bubm46MnIMHa4gudbevfsydPv778QrQmx+Zo2jLOEK1dAlReva2po2YRkyly1ruS5yudMY4PK+p0c5Bs/mV9Y4yhIuoxbFxUEYOlyB9NBTPXPdQ0/11IlXtNj8yhpHWcIVqqHfu3dPz1/buTB0GgNcxxbElZcTr4iy+ZE1jrKEy4g5dDsXhk5jgMt7Q6Acw2HzI2scZQlXqIYue7wlw5tc33zzjTbu+/fvp78nK88lc5ydw1kwdBpDqXB5OWRLOYbDpnMIfP21qxwClCVcxs6hX7lyRWeKO57k5dtvv9WJXYpJYYeh0xjiyOU2OxzlaAabl+VIWcJllKGfT1Vs6aE/evQobeqydUz2lds5nAVDpzGUAteGJFmS7HCp/xMvypKyhMtIQ5cUr9Z8ufTUJRGM9frUqVMFj0/F0GkMpcDl9ZYnytGA0RaPcrtTlnAZY+jJZFKfh358MZwcsJLttDMMncZQalx+JCWhHENeD+FhbnfKEi6j9qHLH/Lrr7+qoaEh9f333+tDWj59+sQ+dBoDXB4mk6EcDXvgelSmlCVcxiWWkXPPf/vtt4Lnn2PoNIaS66F7lEyGcjSHzasUvpQlXEYa+k8//aSH2h8/foyhY+hw+ZBMhnI0LKeAB4fsUJZwYegYOo2B7GKUY8hsXiWZoSzhwtAxdBpDBLjI/x1fNq+SzFCWcGHoGDqNIQJcXichoRzNYvOifClLuIwz9O3tbfX69WvXZ5pj6DSGuHB5nYCEcjSPzYsypizhMsbQJW/71atX//J9ye0u3xejxNBpDKXI5df52ZSjWWxuk8xQlnAZY+jXr1/XGeGO6+7du3oIPio9dgwdLi+5/EgmQzmayeY2yQxlCVfohj45OakzwbW2tmrjlq+tS05cq6mpIfUrjaGkubxOJkM5msvmpqwpS7hCN/SmpqaCZ6FfvnzZ9v3kYJednR1H7/fiPhg6XH5weZkalHI0m83taAxlCVfoht7W1qaqqqp0znYxb/naumpra9WtW7fU4uKi7d6+/F5jY6Nqb29Xu7u7ed8vf6z0/g8ODlzdB0OHyy8uLw/voBzNZ3OzXoKyhMuYOfTBwUF17tw5V4e7VFRUqNXVVf16YGBA3zPfv2d9iMg0dKf3wdDh8ovLz9XtlKOZbG7KnLKEy7hta8Vqenpa9/YtJRIJ3cPOJ+l5i6FnDrsXcx8MHS4/uPzae045ms1WbLlTlnDFxtDHx8dVV1dX+rX0sKUH7tTQ7d4nc44/26lxXlxe3svry1S22HCtrOiemvyfeJUYW5FlT1nClet+kTN0WRHf29ubfr2xsaHNdn9/35GhF3Mfeuhwec3l595zytF8tmLWTlCWcMWqh97d3e1JD93pfTB0uLzk8nvvOeVoPlsxuxsoS7hiY+gzMzNH5r7n5+eLmkMv5j4YOlxec/m595xyjAab0zpAWcIVG0OXxDOyOn1F5p9S6u/vP7I6va+vT01NTRU09EL3wdBppH5z+b33nHI0n62YURrKEq7YGLq1f7yyslInq5HMc3LQi6WGhgadL96SbEerq6vThl5fX3/kZ/nug6HTSP3m8nvvOeUYDTan6ygoS7hiZegi2VPuRd73Yu+DocPlhiuIveeUYzTYnNYFyhKu2Bk6udxpDFHmCmLvOeUYHTZdH1paKEu4MHQMncYQJa6t+/e/9MhmZ4kXdf9LLz1VF6RObD18SFnChaFj6DSGKHClH9zDw8SLun/0g17KzPUiybdvKUu4MHQMncZgOpdeAGVzaJV4lR6bnS1slCVcGDqGTmMImSvIRDKUY/TY7NYPyhIuDB1DpzEYwBVUIhnKMZpsdkZwKEu4MHQMncYQMtfW6KitOVLiVbpsdtZYUJZwYegYOo0hRC4nq5iJV2mziZnn2wVBWcKFoWPoNIYQuZzsMyZesOXLIkhZwoWhY+g0hpC4gs4KRzlGny1fnn/KEi4MHUOnMYTEFdSZ55RjvNhyLaCkLOHC0DF0GkMIXGFuVaMco8smCyel3mRbc0FZwoWhY+g0hhC4gjwilXKMF5vOHJdlcRxlCReGjqHTGELgCvKIVMoxfmzZFlNSlnBh6Bg6jSFgrjAOYaEc48WWbV86ZQkXho6h0xgC5ArrEBbKMX5sx/elU5ZwYegYOo0hQK6wDmGhHOPJljl1Q1nChaFj6DSGgLhMWdlOOcaHLXNxJWUJF4aOodMYAuIKM2c75RhPtsxtbJQlXLEz9MPDQ7Wzs+P5ezF0GqkrruXlUHO2U47xZbO2sUkdI15wxcbQJycnVVVVlWpsbFTt7e1qd3e3qPfW1NSokydPpq+mpiYMnUbqrlFdumTU3DnlGC82mUtXly8TL7jiYejJZFJVVFSo1dVV/XpgYEANDg4W9V4x9IWFBd2Dl0tMGkOnkRbdg5Kh9r//3YhtapRjPNnCPrWPOoahe6rp6WnV1taWfp1IJHTvu5j3iqEvLi4y5E5j8GyO8/Pz58SLuu8vV6qOmbZGgzqGoRel8fFx1dXVlX4tve+ysrKi3iuGfu3aNXXnzh01MzOT9R6ZQ/LHJQXixeXlvby+TGUzjus//9EPWbW3R7yo+/5ypeqY/vD47BnxguvI/SJn6CMjI6q3tzf9emNjQ5vt/v6+4/eOjo6qiYkJ9eDBA1VdXa3GxsboofOp2/G1/WdGOBlyJ17U/cB2UkidS9U94gVXpHvo3d3dtnvodt8ri+cyh+cxdBqpkznN7T8frMSLuh8Ul0mphaljGHpRkqHxTOOdn5/POYfu5L2zqUbR3NyModNInWfwyljVTryo+0FymXL4D3UMQy9Ke3t7euX6ysqKft3f339k5XpfX5+ampoq+N719fX0gjgx556eHv1zDJ1G6mhV+7EeEvGi7gfJZcrxvNQxDN3VPvTKykq9b7y1tVVtb2+nf9bQ0KCG5TCDAu9dSjUAmTevq6vTi+M6Ojry7mfH0OHKlbmLeFH3w+JK18PUh0vihaFHNlPcwcGB2tracvVeCejm5qYjI8fQ4Ur3zrNsHSJe1P2guayRou2QF8hRxzB0crnzUIscV+aqduJF3TeBK1+dJF4YOoaOodNI85j59tAQ8aLuG8VljRqtv3pFvDB0DB1Dp5EWNPPUAzOXmRMv6n7YXDq5UUhz6tQxDB1D56EWCa5CPXPiRd03hSusOXXqGIaOofNQM54r/YAsYObEi7pvClcYc+rUMQwdQ+ehZjSX021BPNSo+6Zw5dqJQbwwdAwdQy85LnkQbj5+7OihyEONum8Kl/VhdHNsLBBTp45h6Bg6DzUjuSwjtx6IxAtDjyKX1N1i6jF1DEPH0HmoxYJLtv3IM7CYng0PNeq+aVx6pClVl79S/m5po45h6Bg6DzWzuO7/aHVoiBeGHiuur/78748f7xMvDB1Dx9DjzfWk8z+uzJyHGnXfdC7L1H+e7CReGDrC0OPHJUOSlpH/3DlJvOCKdczEzC1j93KxHHUMQ8fQeaiFyiWL39K98qFHxAuu0ojZo6G0qXu1V506hqFj6DzUQuHK7JXL5VVPhYcadT8qXLoNZPy3tPSKeGHoGDoPtehwLb1a8sXIeahR96PKJT10y9SfTH9XdJugjmHoGDoPtUC4xMgnrk+njdyvPbk81Kj7UeQSExczt4ydLZsYOobOQ83IbFmZRv7ku2lfs2bxUKPuR5lLht0zh+GdGDt1DEPH0Hmoec5lPYAyh9bFyKWXTrzgImbO59f1/vU/DZ94Yeie6/DwUO3s7Lh+r5P7YOhmcmX2IDJN3BpaD8LIeahR9+PIlZllLnOePVvbI14YelGanJxUVVVVqrGxUbW3t6vd3d2i3uvkPhi6OZc8RCyuzG1nx+fHgzplChOg7sedy2pLmfPsmcPyFlcYbY5yjLChJ5NJVVFRoVZXV/XrgYEBNTg46Pi9Tu6DoYdn3Pn2jWde+X6HhwdcxMzbefbjmeeyGXyu36EcMfS0pqenVVtbW/p1IpHQPWyn73VyHwzdP4POdWUz7czL4jKtR4AJUPdLjctqg8L1VZ7/MofqvXxWUI4RNvTx8XHV1dWVfi097LKyMsfvdXIfDN3bjGx2r2yf9DMfHjxs4SJm5nFlM+NsQ/WF/vNqaymGbrChj4yMqN7e3vTrjY0NdfLkSbW/v+/ovXbvI9+zruOSAvHicmp0Ub5sxyWZzPtzL+Pv5QVXPLiImfdcyeSm/Wdiifzndfwj2UPv7u623UPP9V4n90EIIYTiJCMMfWZm5sjc9/z8fM6573zvdXIfhBBCCEP3WHt7e3p1+srKin7d399/ZHV6X1+fmpqaKvjeQvdBCCGEMHSfJfvHKysrVVNTk2ptbVXb29vpnzU0NKjh4WFb7833syCVbX7eFJnKBhdcxAwuuGJg6KKDgwO1tbXl+r1O7sNDDS64qPtwwYWhIx5qcMFF3YcLLgwdIYQQQhg6QgghhDB0hBBCCENHCCGEEIaOUHbJOfabm5tFpT30m+vTp09Gxuzw8NAoFomVqTIpVqbXLVPbIsLQA1dNTc2RnPGyL940vXjxQp06dUotLi6GzvL+/Xudb6Curk7V1taq5uZmtba2FjqXPNAsrvr6etXT05P1jIGwJAc4SBnKVs2wJTkgqqqqdHbG9vZ2tbu7a1R9NylWJtctU9uiqc8uk573GLqPBbywsKB7BHLJSW4maXl5WbW0tOgkPCY0CkkAJPGyJIfsSKY/E3opFpeU46VLl9Tz58+NKEPJgihnFcgDJGyTSiaTOkujnJ8gGhgYMCpLo0mxMr1umdoWTX12mfS8x9B9LGBTKlu2B4k0iKWlJf0J3ETO69ev69PzTJMYlUkPN+kFi0mFPZQ8PT195ByFRCJh3DkKpsQqKnXLxLZo6rPLlOc9hu5jAV+7dk3duXNHHxpjiqR3cvHixTSTaYYuB+rcuHFDx+7Dhw/GlevZs2fVxMQEJnVMctJhV1dX+rWJJx2abuim1S3T2qLJzy5TnvcYugeSoZYHDx7oy2qQo6Oj+mv5XnV1tRobGzOCTXoB9+7dUx8/ftSXVERpuEEPEWWLmUjmYaVhyPCjzHmaUJaWHj16pOfGwljAlIvLFJOSHpwMzVra2NjQXCatNzDZ0MOsW7kUdlvMNoJhwrMrm0x53mPoHui3337TlU0uKdBsDSNzODJMNll4I4tdrEsWlkgFfPPmjVExE4PIPNs+bK7Xr1+rM2fOqHfv3hlVx0zqoWeWFz10+wq7btn5sBZGWzwuU55ddj4IhfW8x9AD0OzsrF4paqJMnUOX43LltDwTJItw5AHy6tUr4+JkiknJMGPmQ0x6TsyhR7tumdgWo/DsCvN5j6H7oPX19XRFk+Eg+WRp4mIXkxqFDCnLw00kc2WdnZ3q9u3boXPJdh3ZwiMGZaJMMam9vT29yn1lZUW/lvpu0ip3Ew3d1Lplals09dll0vMeQ/dBsgJThoLkk7fM83R0dBi3J9e0RjE3N6f3MAuPDD9euXLFiIU40jvJ3F9qXSbM28nwu9Qx4ZF9zMPDw6HyyFCjbCWSuWDp0cn2J1NkWqxMrlumtkVTn10mPe8xdJ8k2ZUkcYSpRm6i5EEmSS2IWXQlPbqtrS0CQVvkeY+hI4QQQghDRwghhDB0hBBCCGHoCCGEEMLQEUIIIYShI4QQQhg6QgghhDB0hBD6iyTjWF9fH4FACENHCJksyd0uWeEkQ1ym5KAWOZ1LMqFJOljRs2fPjMy7jRCGjhAqeUl6UElVmnlUpGTMunnzpjp9+rT69ttv0/+XFKK//PILQUMIQ0cImaQbN27o4yvF0KWXLvmr7969m/55IpFQ5eXl+udynracX40QwtARQobpwoUL6cNEpBcuPXDrzOxff/1VH50q3xNTl//LCVSSGxwhhKEjhAxTtiF3OdTj4sWLanR0VD1//lybuZy6JsPuP/30E0FDCENHCEXB0EUyjy4SQ5feO0IIQ0cIRdDQM3vr+/v7BAohDB0hZLJqa2u1oY+MjBAMhDB0hFBUdevWLW3oNTU1anBwUA0NDREUhDB0hFDUJIliLl++nN6+dvXqVYKCEIaOEIqq9vb21NbWlp4zRwhh6AghhBDC0BFCCCEMHSGEEEIYOkIIIYQwdIQQQghh6AghhBDC0BFCCCEMHSGEEEIYOkIIIYQ80v8BBf1Gd8qFGXQAAAAASUVORK5CYII=\"/>","value":"#incanter_gorilla.render.ChartView{:content #object[org.jfree.chart.JFreeChart 0x57d37f09 \"org.jfree.chart.JFreeChart@57d37f09\"], :opts nil}"}
;; <=

;; **
;;; For one sample t-tests one can use `t-test` function without `:y` option and with appropriate `:mu` option (default 0).
;; **

;; **
;;; As an example of one sample t-test
;;; we can calculate a 95% confidence interval for the average length of pregnancies 
;;; (`:weeks`).
;; **

;; @@
(def weeks
  (filter (partial not= "NA") (i/sel nc :cols :weeks)))

((s/t-test weeks
          :mu (s/mean weeks)
          :conf-level 0.95
          :alternative :two-sided) :conf-int)
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-double'>38.15279117659766</span>","value":"38.15279117659766"},{"type":"html","content":"<span class='clj-double'>38.516547500757056</span>","value":"38.516547500757056"}],"value":"[38.15279117659766 38.516547500757056]"}
;; <=

;; **
;;; * * *
;;; 
;;; ## On your own
;;; -   Interpret obtained 95% confidence interval for the average length of pregnancies.
;;; 
;;; -   Calculate a new confidence interval for the average length of pregnancies at the 90% 
;;; confidence level.
;;; 
;;; -   Conduct a hypothesis test evaluating whether the average weight gained by 
;;; younger mothers is different than the average weight gained by mature mothers.
;;; 
;;; -   Now, a non-inference task: Determine the age cutoff for younger and mature 
;;; mothers. Use a method of your choice, and explain how your method works.
;;; 
;;; -   Pick a pair of numerical and categorical variables and come up with a 
;;; research question evaluating the relationship between these variables. 
;;; Formulate the question in a way that it can be answered using a hypothesis test
;;; and/or a confidence interval. Answer your question using the `t-test` 
;;; function, report the statistical results, and also provide an explanation in 
;;; plain language.
;; **
