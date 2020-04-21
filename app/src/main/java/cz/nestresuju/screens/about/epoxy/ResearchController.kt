package cz.nestresuju.screens.about.epoxy

import com.airbnb.epoxy.EpoxyController

/**
 * Epoxy controller for displaying research info.
 */
class ResearchController : EpoxyController() {

    override fun buildModels() {
        // TODO: remove testing data
        researchText {
            id("txt1")
            researchText("Existenci interního dokumentu, který do budoucna může ovlivnit podobu české zahraniční politiky nejenom vůči Číně, ale i třeba Rusku, nastínil ministr zahraničí a místopředseda sociální demokracie Tomáš Petříček v neděli v Otázkách Václava Moravce. Redakce z důvěryhodných zdrojů získala kompletní text analýzy, kterou vypracoval odbor zahraničněpolitických analýz a plánování ministerstva.")
        }

        researchSubsection {
            id("sub1")
            headlineText("Analýza ministerstva zahraničí")
            contentText("\"Já jsem zadal ministerstvu zahraničních věcí analýzu dopadů krize a toho, jak bude vypadat svět po této krizi. Velká část věcí, na které jsme byli zvyklí, možná nebude fungovat jako doposud,\" uvedl Petříček a upozornil, že bude třeba se vymanit ze závislosti na Číně, kde se dnes vyrábí většina ochranných pomůcek i velké množství léků.")
        }

        researchSubsection {
            id("sub2")
            headlineText("Fiat")
            contentText("Automobilka ve své krasojízdě pokračovala i nadále a v roce 1984 dokonce na italském trhu držela 62procentní podíl. To je z dnešního pohledu naprosto neuvěřitelné číslo, pročež vlastně nepřekvapí, že krátce poté Fiat převzal rovněž Alfu Romeo a následně také Maserati. To se však již psal rok 1993 a značku čekal sešup, který trvá dodnes a jenž ji v podstatě srazil až na úplné dno. V roce 1998 byl její tržní podíl už jen 41procentní a během nadvlády tehdejšího šéfa Paola Fresca klesl dokonce pod 30 procent.")
        }
    }
}