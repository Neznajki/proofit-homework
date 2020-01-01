# proofit-homework
homework for proofit

#estimation
* code writing 3 hours
* JUnit learning about 4-5 hours
* tests writing about 3-4 hours

# usage
* {projectDir}/src/main/resources (as batch api requests)
* {projectDir}/src/main/resources/incomingRequest1.json (as single api requests)
* note feel free to pass any file that matches specification (it will result as scucess)
* in case you will pass invalid folder or file it will result as error
* in case of error response response will be JSON encoded string
* in case of success response response will be
```json
 {
  "incomingRequest" : {
    "policyNumber" : "LV19-07-100000-1",
    "policyStatus" : "APPROVED",
    "policyObjects" : [ {
      "name" : "flat",
      "policySubObjects" : [ {
        "name" : "tv",
        "sumInsured" : 1.0,
        "riskType" : "WATER"
      }, {
        "name" : "refrigerator",
        "sumInsured" : 80.0,
        "riskType" : "FIRE"
      } ]
    }, {
      "name" : "human",
      "policySubObjects" : [ {
        "name" : "balls",
        "sumInsured" : 10.0,
        "riskType" : "WATER"
      }, {
        "name" : "hair",
        "sumInsured" : 30.0,
        "riskType" : "fire"
      } ]
    }, {
      "name" : "car",
      "policySubObjects" : null
    } ]
  },
  "premium" : 5.42
}
```


#philosophy
* incoming api replaced with file reading to reduce task complexity in case you need complex API please specify rpc protocols and whatever to describe entry point
* removed Premium field from incoming request and moved to response object, as there is no description what to do if the object default value is set. so it will result as error, as there wasn't described incoming outgoing parameters
* unit tests split made due investigating of unit test engine inside Java.
* in case each PolicyObject should be calculated separated, estimate on job done will be approx 5-10 minutes, currently it counts global per single incoming request, 1 hour would be my ETA
* new class creation is hardcoded as no restriction to database was made, could be reworked to some RuleSetEntity that stores data about coefficient calculations, it would increase code complexity.

#new risk type support implementation
* create risk class
```java
public class MyCoolRiskTypeCollector extends AbstractCollector {
    @Override
    protected double getCoefficientRate() {
        //do whatever you need to support any hardcoded formulas
        if (this.getTotalSum() > xxx) {
            return x.xxx;
        }
        return x.xxx;
    }
}
```
* define new collectors.
```java
public class SumCollector {
    //..
    public SumCollector()
    {
        //..
                this.supportedCollectors.put("MY_COOL_RISK_TYPE", new MyCoolRiskTypeCollector());

    }
    
    //..
}
```
* notice please update tests for new type