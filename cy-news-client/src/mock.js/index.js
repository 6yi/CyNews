import Mock from 'mockjs'

Mock.mock('/login',{
    
    
      "mtime": "@datetime",//随机生成日期时间
      "score|1-800": 800,//随机生成1-800的数字
      "rank|1-100":  100,//随机生成1-100的数字
      "stars|1-5": 5,//随机生成1-5的数字
      "nickname": "@cname",//随机生成中文名字
      "token|40" : "W"   //重复生成token
})

Mock.mock('/content',{
      "img": Mock.Random.image("200x100")
})