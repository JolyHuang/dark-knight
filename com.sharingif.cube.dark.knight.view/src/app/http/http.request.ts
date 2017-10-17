export class HttpRequest {
  ip: string;
  port: number;
  address: string;
  useHttps: boolean;
  contextPath: string;
  url: string;
  data: any;
  success: object;
  failure: object;
  getFullUrl() {
    if(this.address !== null) {
      if(this.useHttps) {
        return "https://"+this.address+"/"+this.contextPath+"/"+this.url;
      } else {
        return "http://"+this.address+"/"+this.contextPath+"/"+this.url;
      }
    }

    if(this.useHttps) {
      return "https://"+this.ip+":"+this.port+"/"+this.contextPath+"/"+this.url;
    } else {
      return "http://"+this.ip+":"+this.port+"/"+this.contextPath+"/"+this.url;
    }
  };
}
