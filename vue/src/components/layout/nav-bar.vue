<template>

  <div>
  <div>
    <div class="animate__animated animate__fadeIn title"  :style="{'background-image': bgUrl}"></div>
    <el-header  class="animate__animated animate__fadeIn">
      <div class="menu-expend">
        <i class="el-icon-menu"></i>
      </div>
      <el-button type="primary" icon="el-icon-edit" circle @click="intoResearch"></el-button>
<!--      <div class="add">-->
<!--        <el-input v-model="addNewLink.link" clearable placeholder="new link"-->
<!--                  @clear="addLink()" class="add_input"></el-input>-->
<!--        <el-button slot="append" icon="el-icon-search" @click="addLink()" class="add_button"></el-button>-->
<!--      </div>-->
    </el-header>
  </div>

  <div class="business wrap">
    <ul class="box clearfix">
      <div v-for="item in linkForm">
      <li>
        <a @click="intoRepo(item.repoName)">
          <h3 v-text="item.repoName"></h3>
          <img :src="Picture" alt="课程"/>
          <div class="word">
            <h4 v-text="item.repoName"></h4>
            <i class="border"></i>
            <p v-text="item.id"></p>
          </div>
        </a>
      </li>
      </div>
    </ul>
  </div>
  </div>

</template>

<script>
import router from "@/router";

export default {
  name: "Homepage",
  data() {
    return {
      bgUrl:'url(https://p0.meituan.net/dpplatform/4ce8553013e2e819c08e6d6ba409bee8473079.jpg)',
      linkForm: [
        {
          id: "123",
          repoName: "name",
        },
      ],
      Picture: "https://p1.meituan.net/dpplatform/520b1a640610802b41c5d2f7a6779f8a87189.jpg",
      addNewLink:{
        link:""
      },
    };
  },
  mounted() {
    this.fetchLink()
  },
  methods: {
    async fetchLink() {
      const { data: res } = await this.$http.get('/repo/all_repo')
      this.linkForm = res
    },
    intoRepo(repoName){
      localStorage.setItem("repoName",repoName)
      router.push({path:'/Visualization'})
    },
    intoResearch(){
      router.push("/research")
    }
  },
}
</script>

<style>
*{
  margin: 0;
  padding: 0;
}
</style>


<style>
.block{
  position: absolute;
  top: 1200px;
  left: 510px;
}
.add{
  position: absolute;
  top: 1000px;
  left: 500px;
}
</style>

<style scoped lang="less">

.title {
  position: fixed;
  top: 0;
  width: 100%;
  height: 100%;
  background-repeat: no-repeat;
  background-size: cover;
  border-bottom: 100px solid #fff;
}

.el-header {
  transition: .2s;
}

.el-header:hover {
  opacity: 1 !important;
}

.el-menu {
  background-color: rgba(0, 0, 0, 0) !important;
}

.el-menu /deep/ .el-menu-item{
  background-color: rgba(0, 0, 0, 0) !important;
}


.el-menu /deep/ .el-menu-item i {
  color: rgba(255, 255, 255);
}

.el-menu /deep/ .el-menu-item:hover i {
  color: white;
}

.el-menu /deep/ .el-menu-item:hover {
  background-color: rgba(0, 0, 0, 0.5) !important;
}
/deep/ .el-link{
  font-size: 25px;
}

.el-link .el-icon--right.el-icon {
  vertical-align: text-bottom;
}


.el-header {
  width: 100%;
  position: sticky;
  top: 0;
  z-index: 9999;
  background-color: rgba(0, 0, 0, 0);
  color: #fff;
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 20px;

  > div {
    display: flex;
    align-items: center;
  }

  img {
    height: 40px;
  }

  span {
    margin-left: 15px;
  }

  button {
    opacity: 0.8;
  }

  .el-menu {
    flex-shrink: 0;
  }

  .logo {
    color: #ffd04b;
  }

  .logo:hover {
    cursor: pointer;
  }

}

.business{
  position: absolute;
  top: 350px;
  left: 125px;
  border-top: 1px solid #ccc;
  border-right: none;
  width: 100%;
}
.business {
  ul {
    li {
      &:hover h3 {
        opacity: 0;
      }

      &:hover img {
        -webkit-transform: scale(1.1);
        -ms-transform: scale(1.1);
        transform: scale(1.1);
      }

      &:hover .word {
        display: block;
        opacity: 1;
        background: rgba(0, 0, 0, 0.6);
      }

      width: 380px;
      height: 220px;
      position: relative;
      float: left;
      overflow: hidden;
      margin-top: 15px;
      margin-left: 15px;

      a {
        h3 {
          position: absolute;
          left: 25px;
          bottom: 20px;
          z-index: 2;
          font-size: 20px;
          color: #fff;
          font-weight: 400;
          opacity: 1;
          filter: alpha(opacity=100);
          -webkit-transition: opacity 0.4s;
          transition: opacity 0.4s;
        }

        img {
          position: relative;
          display: block;
          z-index: 1;
          width: 100%;
          -webkit-transition: -webkit-transform 0.4s;
          transition: -webkit-transform 0.4s;
          transition: transform 0.4s;
          transition: transform 0.4s, -webkit-transform 0.4s;
        }

        .word {
          opacity: 0;
          filter: alpha(opacity=0);
          position: absolute;
          top: 0;
          left: 0;
          right: 0;
          bottom: 0;
          z-index: 3;
          padding-top: 20%;
          text-align: center;
          -webkit-transition: opacity 0.4s;
          transition: opacity 0.4s;

          h4 {
            font-size: 20px;
            color: #fff;
            font-weight: 400;
          }

          .border {
            display: block;
            margin: 10px auto;
            width: 22px;
            height: 1px;
            line-height: 0;
            font-size: 0;
            background: #4681e6;
          }

          p {
            font-size: 14px;
            color: #fff;
          }
        }
      }
    }
  }
}
</style>
