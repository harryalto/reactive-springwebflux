For this project to work there are few prerequisites
1. Installation of mongoDB
2. Installation of json-server ( for faking server responses)

For MongoDB, use docker to install on your local.Use this link 
https://www.thepolyglotdeveloper.com/2019/01/getting-started-mongodb-docker-container-deployment/

For installing json-server and how it works refer to this link : https://medium.com/codingthesmartway-com-blog/create-a-rest-api-with-json-server-36da8680136d

This is the db.json file used 

```
{
   "projects":[
      {
         "projectId":2,
         "projectName":"Hello",
         "projectDesc":"Demo project"
      },
      {
         "projectId":3,
         "projectName":"Hello123",
         "projectDesc":"Demo project series"
      },
      {
         "projectId":4,
         "projectName":"Hello456",
         "projectDesc":"Demo project repeat"
      }
   ],
   "2":[
      {
         "teamMember":"abc",
         "teamMemberRole":"Manager"
      }
   ],
   "3":[
      {
         "teamMember":"efg",
         "teamMemberRole":"Manager"
      }
   ]
}

```
