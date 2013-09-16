(ns instasearch.core
  (:use     [cheshire.core :only [generate-string parse-string]])
  (:require [org.httpkit.client :as http] 
            
         ))


(def client-id "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX") ; Replace with own key. 


(def options {:timeout 5000             ; ms
              ;:basic-auth ["user" "pass"]
              ;:query-params {:param "value" :param2 ["value1" "value2"]}
              :user-agent "TDC/instasearch 0.0.1"
              ;:headers {"X-Header" "Value"}
              })

;; async 
(defn search-async [tag] "search for a tag"
   (http/get (str "https://api.instagram.com/v1/tags/" tag "?client_id=" client-id) 
             options
             (fn [{:keys [status headers body error]}] ;; asynchronous handle response
    	        (if error (do (println "Failed, exception is " error)
                            {:error error}) 
                (do 
            	    (println "Async HTTP GET: " status)
                  (prn body)
               )))))


;; synchronous
(defn count-tag [tag]
    (let [{:keys [status headers body error] :as resp} @(http/get (str "https://api.instagram.com/v1/tags/" tag "?client_id=" client-id))]
      (if error (do (println "Failed, exception is " error)
                    {:error error}) 
        (do 
          (println "Sync HTTP GET: " status)
          {:count (get-in (parse-string body) ["data" "media_count"])}
        ))))

(defn search-tag [tag] 
  (let [{:keys [status headers body error] :as resp} @(http/get (str "https://api.instagram.com/v1/tags/" tag "/media/recent?client_id=" client-id))]
  (if error (do (println "Failed, exception is " error)
                {:error error}) 
    (do 
      (println "Tag search: " tag )
      (println "Sync HTTP GET: " status)
      body
    ))))

(defn pagination-next [uri] 
  (let [{:keys [status headers body error] :as resp} @(http/get uri)]
    (if error (do (println "Failed, exception is " error)
                {:error error}) 
      (do 
        (println "Page search:" uri)
        (println "Sync HTTP GET: " status)
        body))))
