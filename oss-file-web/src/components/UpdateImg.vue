<template>
  <div>
    <el-dialog
      title="修改头像"
      :visible.sync="showBoolean"
      @close="close()"
      width="20%"
      center>
      <div class="updateImg">
        <el-upload
          ref="upload"
          class="avatar-uploader"
          :action="action"
          :show-file-list="false"
          :on-success="handleAvatarSuccess"
          :on-change="imgChangGe"
          :auto-upload="false"
          :headers=header
          :file-list="fileList"
          name="file"
          :before-upload="beforeAvatarUpload">
          <img v-if="imageUrl" :src="imageUrl" class="avatar">
          <i v-else class="el-icon-upload avatar-uploader-icon"></i>
        </el-upload>
        <div class="updateImg-button-all">
          <Button @click="cancel()" class="updateImg-button">返 回</Button>
          <Button type="primary" @click="confirm()" class="updateImg-button">上传到服务器</Button>
        </div>
      </div>
    </el-dialog>
  </div>
</template>

<script>
    import {mapMutations} from "vuex";

    export default {
        name: "UpdateImg",
      props: {
        updateImgVisible: {
          type: Boolean,
          default: false,
          required: true,
        },
      },
      data() {
        return {
          header:{token:localStorage.getItem("token")},
          action:this.$BaseUrl.URL_HTTP_PREFIX+'/user/updateUserImg',
          name: '',
          showBoolean: false,
          imageUrl: '',
          fileList:[]
        };
      },
      watch: {
        updateImgVisible(bool) {
          this.showBoolean = bool;
          if (!bool){
            this.imageUrl=''
          }
        }
      },
      methods: {
        ...mapMutations([
          'setUserImg',
        ]),
        close() {
          this.name='';
          this.cancel();
        },
        cancel() {
          this.$emit("closeUpdateImg");
        },
        confirm() {
          console.log("上传触发事件",this.fileList)
          this.$refs.upload.submit();
          // this.$emit("confirmUpdateImg", this.name);
        },
        imgChangGe(file,FileList){
          console.log("file",file)
          console.log("FileList",FileList)
          this.fileList=FileList.length>0? FileList.slice(FileList.length-1):[];
          this.imageUrl = URL.createObjectURL(file.raw);
        },
        handleAvatarSuccess(res, file) {
          console.log("上传成功",res,file)
          this.imageUrl = ''
          this.setUserImg(res.data)
          this.$message.success("修改成功")
          this.$emit("confirmUpdateImg");
        },
        beforeAvatarUpload(file) {
          console.log("上传之前",file)
          const isJPG = file.type === 'image/jpeg';
          const isLt2M = file.size / 1024 / 1024 < 2;
          const isPNG= file.type ==='image/png';

          if (!isJPG && !isPNG) {
            this.$message.error('上传头像图片只能是 JPG,PNG 格式!');
          }
          if (!isLt2M) {
            this.$message.error('上传头像图片大小不能超过 2MB!');
          }
          return (isJPG || isPNG) && isLt2M;
        }

      }
    }
</script>

<style scoped>
  .updateImg {
    display: flex;
    flex-direction: column;
    padding-left: 80px;
  }

  .updateImgInput {
    outline-style: none;
    border: 1px solid #ccc;
    border-radius: 3px;
    padding: 13px 14px;
    /*width: 620px;*/
    font-size: 14px;
    /*color: #bfae90;*/
    height: 40px;
    width: 280px;
    font-family: "Microsoft soft";
    margin-left: 30px;
  }

  .updateImg-button-all {
    margin-top: 20px;
    display: flex;
    flex-direction: row;
    justify-content: flex-end;
  }

  .updateImg-button {
    margin-right: 22px;
  }
  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
    border: 1px solid #42b983;
  }
  .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }
</style>
