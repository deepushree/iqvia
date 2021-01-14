# jpa-spec-with-quartz-and-api

> The original jpa-spec repository was created in order to provide sample data to be
> used with a DZone article designed to communicate the benefits of Spring Data, 
> JPA and Specifications. 
> 
> A second repository, jpa-spec-with-quartz, is a clone of jpa-spec with Quartz scheduler 
> functionality added to the project, to demonstrate using the `spring-boot-starter-quartz` 
> implementation by Spring.  It was also featured in a DZone article.
> 
> This repository, jpa-spec-with-quartz-and-api, is a clone of jpa-spec-with-quartz and adds a
> Schedule API to be able to use RESTful commands to view the Quartz scheduler and manage jobs. 
> 
> It will be featured in the following DZone article:
> 
> [Adding a RESTful API for the Quartz Scheduler](https://dzone.com/articles/adding-a-restful-api-for-the-quartz-scheduler)

## Using This Repository

The repository is a Spring Boot application, which can be executed by running the `JpaSpecWithQuartzAndApiApplication` files in the `com.gitlab.johnjvester.jpaspec` package.  

When the application starts, an in-memory H2 database will be created and initialized with some default data.  The inserts for this data can be found in the `src\main\resources\data.sql` file.

API documentation for the `jpa-spec-with-quartz-and-api` utilizes [Swagger](https://swagger.io/), which can be found at the following URL:

`http://localhost:9000/swagger-ui.html`

## Quartz Scheduler

Quartz version 2.3.0 is currently being utilized via spring-boot integration and the following dependency:
```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-quartz</artifactId>
</dependency>
```

Please review the [README.md](https://gitlab.com/johnjvester/jpa-spec-with-quartz/blob/master/README.md) from the jpa-spec-with-quartz repository for more information.

## Scheduler API 

This section will document the Scheduler API.

### Scheduler API - Scheduler Information

With Quartz fully enabled, up and running, the following URI can be used to retrieve information about the Quartz instance:

`GET http://localhost:9000/scheduler/information`

A `200 OK` response will include a payload similar to what is displayed below:

```
{
    "version": "2.3.0",
    "schedulerName": "MyInstanceName",
    "instanceId": "Instance1",
    "threadPoolClass": "org.quartz.simpl.SimpleThreadPool",
    "numberOfThreads": 10,
    "schedulerClass": "org.quartz.impl.StdScheduler",
    "jobStoreClass": "org.springframework.scheduling.quartz.LocalDataSourceJobStore",
    "numberOfJobsExecuted": 1,
    "startTime": "2019-07-14T20:02:11.766+0000",
    "inStandbyMode": false,
    "simpleJobDetail": [
        "DEFAULT.Member Statistics Job - next run: Sun Jul 14 16:03:11 EDT 2019 (previous run: Sun Jul 14 16:02:11 EDT 2019)",
        "DEFAULT.Class Statistics Job - next run: Sun Jul 14 16:05:00 EDT 2019 (previous run: null)"
    ],
    "clustered": false,
    "schedulerProductName": "Quartz Scheduler (spring-boot-starter-quartz)"
}
```

#### Scheduler API - Retrieving Job Keys

For Quartz, jobs are retrieved via a `JobKey` which is a two-attribute set containing a job name and job group.  The `jobKeys` URL provides a list of available job keys:

`GET http://localhost:9000/scheduler/jobKeys`

A `200 OK` response will provide a payload similar to what is displayed below:

```
[
    {
        "name": "Member Statistics Job",
        "group": "DEFAULT"
    },
    {
        "name": "Class Statistics Job",
        "group": "DEFAULT"
    }
]
```

#### Scheduler API - Retrieve Job Details

To see the follow details for a given `JobKey`, the following URL can be utilized:

`GET http://localhost:9000/scheduler/jobDetail?name=Member+Statistics+Job&group=DEFAULT`

A `200 OK` response will be received and include a payload similar to what is displayed below:

```
{
    "name": "Member Statistics Job",
    "group": "DEFAULT",
    "description": null,
    "jobClass": "com.gitlab.johnjvester.jpaspec.jobs.MemberStatsJob",
    "concurrentExectionDisallowed": true,
    "persistJobDataAfterExecution": false,
    "durable": true,
    "requestsRecovery": false,
    "triggers": [
        {
            "name": "Member Statistics Trigger",
            "group": "DEFAULT",
            "description": null,
            "calendarName": null,
            "nextFireTime": "2019-07-14T20:48:15.343+0000",
            "previousFireTime": "2019-07-14T20:47:15.343+0000",
            "startTime": "2019-07-14T20:46:15.343+0000",
            "endTime": null,
            "finalFireTime": null,
            "priority": 0,
            "misfireInstruction": 4,
            "triggerType": "SimpleTriggerImpl",
            "repeatInterval": 60000,
            "repeatCount": -1,
            "timesTriggered": 2,
            "timeZone": null,
            "cronExpression": null,
            "expressionSummary": null
        }
    ]
}
```

#### Scheduler API - Deleting a Job

In order to delete a job, the following URI is designed to handle this request:

`DELETE http://localhost:9000/scheduler/deleteJob?name=Member+Statistics+Job&group=DEFAULT`

If successful, a `202 ACCEPTED` response will be received, with a payload similar to what is listed below:

```
{
    "type": "DELETE",
    "name": "Member Statistics Job",
    "group": "DEFAULT",
    "result": true,
    "status": "DEFAULT.Member Statistics Job has been successfully deleted"
}
```

Please note - the job will also need to be removed from the `com.gitlab.johnjvester.jpaspec.config.QuartzSubmitJobs` class, to avoid 
the deleted job from being added to the schedule again on the next restart of the Spring Boot server.  In the example above, this would 
translate into deleting the following methods:

```
@Bean(name = "memberStats")
public JobDetailFactoryBean jobMemberStats() {
    return QuartzConfig.createJobDetail(MemberStatsJob.class, "Member Statistics Job");
}

@Bean(name = "memberStatsTrigger")
public SimpleTriggerFactoryBean triggerMemberStats(@Qualifier("memberStats") JobDetail jobDetail) {
    return QuartzConfig.createTrigger(jobDetail, 60000, "Member Statistics Trigger");
}
```

## More Information

See the following links for additional information:

* [My original jpa-spec repository](https://gitlab.com/johnjvester/jpa-spec)
* [My original jpa-spec-with-quartz repository](https://gitlab.com/johnjvester/jpa-spec-with-quartz)

Made with ♥ by johnjvester.