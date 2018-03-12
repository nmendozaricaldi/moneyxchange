import { Component, OnInit,ElementRef ,ViewChild} from '@angular/core';
import {AppService, MoneyChange, MoneyRate,StompService} from '../app.service';
import AutoNumeric from 'autonumeric';
import {URLSearchParams} from '@angular/http'


@Component({
  selector: 'foo-details',
  providers: [AppService],  
  templateUrl: './foo.component.html',
  styleUrls: ['./foo.component.css']
})
export class FooComponent implements OnInit {
  @ViewChild('resultEl') result_el:ElementRef;
  @ViewChild('baseEl') base_el:ElementRef;
  public moneyChange = new MoneyChange(0,'0 EU');
  public nomeyRate  = new MoneyRate("USD", new Date(), {});
  private nomeyRateUrl = 'http://localhost:8082/money.change.api/latest?';  

  private selectSymbol  = "EU";
  private options = {base:"USD",symbols:["EU"]};

  private resultEl:any;
  private baseEl:any;

  private stompClient;
  private stomp;
  private serverUrl = "ws://localhost:8082/money.change.api/socket";
  private subscription : any;

  constructor(private _service:AppService,stomp: StompService) { 
    let params = new URLSearchParams();
    for(let key in this.options){
        params.set(key, this.options[key]) 
    }
    this._service.getRates(this.nomeyRateUrl+ params.toString())
      .subscribe(
                  data => this.nomeyRate = data,
                  error =>  this.nomeyRate.base = 'Error');
    //configuration
    stomp.configure({
      host:'http://localhost:8082/money.change.api/socket',
      debug:true,
      queue:{'init':false}
    });

    //start connection
    stomp.startConnect().then(() => {
      stomp.done('init');     
      console.log('connected'); 
      //subscribe
      this.subscription = stomp.subscribe('/rates', this.response);
    });

                
  }
  //response
  public response = (data) => {
    this.nomeyRate = data;
    this.calculte();
  }

  ngOnInit() {
    
  }

  ngAfterViewInit()
  {
    this.baseEl=new AutoNumeric(this.base_el.nativeElement, { currencySymbol : '$',decimalCharacter:"." ,digitGroupSeparator:",",decimalPlaces:4,currencySymbolPlacement    : AutoNumeric.options.currencySymbolPlacement.suffix,});
    this.resultEl=new AutoNumeric(this.result_el.nativeElement, { currencySymbol : '€',decimalCharacter:"." ,digitGroupSeparator:",",decimalPlaces:4,currencySymbolPlacement    : AutoNumeric.options.currencySymbolPlacement.suffix,});
    this.resultEl.set(0);
    this.baseEl.set(0);
  }
  calculte(){
    if(!this.nomeyRate.rates[this.selectSymbol]){
      alert('start up api moneyxchange');
      return;
    }
    this.moneyChange.result = this.baseEl.rawValue*this.nomeyRate.rates[this.selectSymbol]+"";
    this.resultEl.set(this.moneyChange.result)
  }
}
