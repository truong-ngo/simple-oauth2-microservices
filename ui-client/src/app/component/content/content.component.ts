import {Component, OnInit} from '@angular/core';
import {Service} from "../../service/service";

@Component({
  selector: 'app-content',
  standalone: true,
  imports: [],
  templateUrl: './content.component.html',
  styleUrl: './content.component.css'
})
export class ContentComponent implements OnInit {
  data: any;

  url = "http://localhost:8082/"

  constructor(private service: Service) {
  }

  ngOnInit(): void {
    this.service.getResource(this.url).subscribe(
      data => this.data = data,
      error => {
        console.log(error.toString());
        alert("error");
      }
    )
  }
}
