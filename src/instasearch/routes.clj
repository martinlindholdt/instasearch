(ns instasearch.routes 
 (:use  compojure.core
        ring.middleware.resource
        ring.middleware.file-info
        ;[clojure.data.json :only (json-str)]
        [instasearch.core :only [search-tag pagination-next count-tag]]
        )

   (:require 
            [compojure.route :as route]
            [compojure.handler :as handler]
            [clj-json.core :as json]
;            [clojure.data.json :as json]
            [ring.util.mime-type :as mime]

            ) )

(defn- assoc-in-param [m param]
  (if (second param)
    (apply assoc-in m param)
    m))

(defn json-response
  "Data is the http body, :status is optional httpcode, :etag is optional calculated etag value and content-type is ex. application/vnd.yoursee+json. :cache-control, :allow and :expires are optional"
  [data content-type & {:as attrs}]
  (-> {:status (or (:status attrs) 200)
       :headers {"Content-Type" content-type
                 "ETag" (str (if (:etag attrs) (:etag attrs) (hash data)))}
       :body (json/generate-string data)}
      (assoc-in-param [[:headers "Cache-Control"] (:cache-control attrs)])
      (assoc-in-param [[:headers "Expires"] (:expires attrs)])
      (assoc-in-param [[:headers "Allow"] (:allow attrs)])))

(defroutes public-handler

  (GET [":context/search/:tag", :context #".[^/]*"] req
    (let [tag (get-in req [:route-params :tag])]
      ;(json-response (search-tag tag)))) 
      (search-tag tag)))

  (GET [":context/search/:tag/count", :context #".[^/]*"] req
    (let [tag (get-in req [:route-params :tag])
          _ (prn "Tag count:" tag)]
      (json-response (count-tag tag) "application/json"))) 
      

  (GET [":context/search/:tag/next", :context #".[^/]*"] req
    (let [;tag (get-in req [:route-params :tag]) 
          next-uri (get-in req [:query-params "uri"])
          max-tag-id (get-in req [:query-params "max_tag_id"])
          _ (prn max-tag-id)]
      (pagination-next (str next-uri "&max_tag_id=" max-tag-id )))) 
  
    
  
  
  (GET [":context/ping", :context #".[^/]*"] []
    {:status 200, :headers {"Content-Type" "text/plain" "expires" "0" "cache-control" "no-cache"} :body "pong"})
  
  ; (GET [":context/alive-info", :context #".[^/]*"] req
  ;   ;(json-response (alive/get-alive-info) mediatype))
  ;   (json-response {:hej "verden"} "application/json"))
    
    
  (GET [":context/console/*", :context #".[^/]*"] req
           (route/resources (str (:context (:params req)) "/console")))
  
  
    ) ;; Handler end 


(defroutes handler
  public-handler
  ;(wrap-basic-authentication protected-handler authenticated? "Brugernavn/password ikke fundet")
  (route/not-found "<h1>Siden findes ikke</h1>")
  )

(def app
  (-> (handler/site handler)
    ;(with-context "/message")
    ;(wrap-resource "public") ; resources the ring way: https://github.com/ring-clojure/ring/blob/master/ring-core/src/ring/middleware/resource.clj
    (wrap-file-info)))