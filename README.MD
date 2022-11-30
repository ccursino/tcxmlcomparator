<a name="readme-top"></a>

<!-- PROJECT LOGO -->
<br />
<div align="center">

<h3 align="center">TCxml Comparator</h3>

  <p align="center">
    Purpose: compare 2 Tcxml files and shows it's differences. It's usefull for after migration validation (ex).
    <br />
    TCxml files are Siemens Teamcenter files generated with the tcxml_export utility. 
    <br />
    Usage: java -jar tcxmlcomparator.jar file1.xml file2.xml
    <br />
    On the application.properties you can define the fields that should not be compared.
    <br />
    <br />
  </p>
</div>


<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#license">License</a></li>
  </ol>
</details>



<!-- ABOUT THE PROJECT -->
## About The Project



<p align="right">(<a href="#readme-top">back to top</a>)</p>



### Built With
* [![Java][Java-img]][Java-url]
* [VTD-XML][VTD-url]

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- GETTING STARTED -->
## Getting Started

This project can be build with Maven 3.8 and Java 11.

### Prerequisites
Java 11

### Installation

Just copy the release folder to your system, or clone the repository and build it with:

```sh
git clone https://github.com/ccursino/tcxmlcomparator.git
cd src
mvn clean package
```

<p align="right">(<a href="#readme-top">back to top</a>)</p>


<!-- USAGE EXAMPLES -->
## Usage

To export a tcxml file:

Enter into tc_menu:

```sh
tcxml_export -u=<user> -p=<pwd> -g=<group> -file=<output_file> -class=<class_name> -optionset=<option_set>
```

Sample:
```sh
tcxml_export -u=infodba -p=xxxxx -g=dba -file=sample.xml -class=Item -optionset=MultiSiteOptSet
```

To compare:
```sh
java -jar tcxmlcomparator.jar file1.xml file2.xml
```


<p align="right">(<a href="#readme-top">back to top</a>)</p>


<!-- LICENSE -->
## License

Distributed under the MIT License. See `LICENSE.txt` for more information.

<p align="right">(<a href="#readme-top">back to top</a>)</p>


<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[contributors-shield]: https://img.shields.io/github/contributors/github_username/repo_name.svg?style=for-the-badge
[contributors-url]: https://github.com/github_username/repo_name/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/github_username/repo_name.svg?style=for-the-badge
[forks-url]: https://github.com/github_username/repo_name/network/members
[stars-shield]: https://img.shields.io/github/stars/github_username/repo_name.svg?style=for-the-badge
[stars-url]: https://github.com/github_username/repo_name/stargazers
[issues-shield]: https://img.shields.io/github/issues/github_username/repo_name.svg?style=for-the-badge
[issues-url]: https://github.com/github_username/repo_name/issues
[license-shield]: https://img.shields.io/github/license/github_username/repo_name.svg?style=for-the-badge
[license-url]: https://github.com/github_username/repo_name/blob/master/LICENSE.txt
[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=555
[linkedin-url]: https://linkedin.com/in/linkedin_username
[product-screenshot]: images/screenshot.png
[Java-img]: https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white
[Java-url]: https://www.java.com/
[VTD-img]: https://search.maven.org/assets/images/xml.svg?style=for-the-badge&logo=react&logoColor=61DAFB
[VTD-url]: https://vtd-xml.sourceforge.io/
