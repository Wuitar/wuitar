# MENU
- [To Base](#Base)`[表示テキスト](anchor)`
- [To Collapse](#Collapse)
- [To List](#List)
- [To Link](#Link)
- [To Table](#Table) 

## Base
# H1 title `# contents`
## H2 title `## contents`
### H3 title `### contents`
#### H4 title `#### contents`
##### H5 title `##### contents`
###### H6 title `####### contents`

*italic*`*contents*`  
**strong**`**contents**`  
***italic strong***`***contents***`  
~~delete line~~`~~contents~~`  
> reference
>
> `> contents`

## Collapse
<details>
  <summary>collapsible section</summary>
  <div>

```java
int a = 0; //<div> とコードブロックの間には空白行が一つ以上必要です。
```
```html
  <details>
    <summary>title</summary>
    <div>
    
    contents
    </div>
  </details>
```
  </div>
</details>

## List
- ul
  - li **nest with two spaces**
    - li
      - li
  - li
<details><summary>code</summary><div>

```
- ul
  - li
    - li
      - li
  - li
```
</div></details>  

1. ol with number
    1. li **nest with four spaces**
    1. li
1. ol
1. ol


## Link
[Google](https://www.google.co.jp/) `[表示テキスト](URL)`  
https://www.google.co.jp/ `URL`  


## Table
|TH|TH|
|--|--|
|TD|TD|
